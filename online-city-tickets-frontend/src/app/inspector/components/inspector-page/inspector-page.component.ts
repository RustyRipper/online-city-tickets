import {
  AfterViewInit,
  Component,
  ElementRef,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Message } from "primeng/api";
import { InputMaskModule } from "primeng/inputmask";
import { MessagesModule } from "primeng/messages";
import QrScanner from "qr-scanner";
import {
  BehaviorSubject,
  Observable,
  combineLatest,
  distinctUntilChanged,
  firstValueFrom,
  map,
} from "rxjs";

import { InspectionApi } from "~/generated/api/services";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i18n/i18n.service";
import { SettingsLinkComponent } from "~/shared/settings/components/settings-link/settings-link.component";

const VEHICLE_PATTERN = /^[A-Z]{3} [0-9]{5}$/;
const CODE_PATTERN = /^[0-9A-Z]{10}$/;

@Component({
  selector: "app-inspector-page",
  standalone: true,
  imports: [
    FormsModule,
    MessagesModule,
    InputMaskModule,
    TopBarComponent,
    SettingsLinkComponent,
  ],
  templateUrl: "./inspector-page.component.html",
  styleUrl: "./inspector-page.component.css",
})
export class InspectorPageComponent implements AfterViewInit, OnDestroy {
  @ViewChild("video") protected video!: ElementRef<HTMLVideoElement>;
  private scanner!: QrScanner;

  private readonly dataSubject = new BehaviorSubject<string | null>(null);
  private readonly data$: Observable<string | null> = this.dataSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  private readonly vehicleSubject = new BehaviorSubject<string | null>(null);
  private readonly vehicle$: Observable<string | null> = this.vehicleSubject
    .asObservable()
    .pipe(
      map((v) => {
        if (!v) return null;
        const number = v.replaceAll("_", "").trim().toUpperCase();
        return number.length === 9 ? number : null;
      }),
      distinctUntilChanged(),
    );

  private readonly messageStates = {
    UNKNOWN_ERROR: {
      severity: "warn",
      detail: this.i18n.t("inspector-page.unknown-error"),
    },
    VEHICLE_NOT_SELECTED: {
      severity: "warn",
      detail: this.i18n.t("inspector-page.vehicle-not-selected"),
    },
    VEHICLE_NOT_FOUND: {
      severity: "warn",
      detail: this.i18n.t("inspector-page.vehicle-not-found"),
    },
    WAITING: {
      severity: "info",
      detail: this.i18n.t("inspector-page.waiting"),
    },
    INSPECTING: {
      severity: "info",
      detail: this.i18n.t("inspector-page.inspecting"),
    },
    VALID: {
      severity: "success",
      detail: this.i18n.t("inspector-page.valid"),
    },
    TICKET_NOT_FOUND: {
      severity: "error",
      detail: this.i18n.t("inspector-page.ticket-not-found"),
    },
    TICKET_NOT_VALIDATED: {
      severity: "error",
      detail: this.i18n.t("inspector-page.ticket-not-validated"),
    },
    TICKET_EXPIRED: {
      severity: "error",
      detail: this.i18n.t("inspector-page.ticket-expired"),
    },
    TICKET_NOT_VALID_FOR_VEHICLE: {
      severity: "error",
      detail: this.i18n.t("inspector-page.ticket-not-valid-for-vehicle"),
    },
  } as const satisfies Record<string, Message>;
  private state: keyof typeof this.messageStates = "VEHICLE_NOT_SELECTED";

  public constructor(
    private readonly inspectionApi: InspectionApi,
    protected readonly i18n: I18nService,
  ) {}

  public ngAfterViewInit(): void {
    this.scanner = new QrScanner(
      this.video.nativeElement,
      ({ data }) => this.dataSubject.next(data),
      { returnDetailedScanResult: true },
    );

    this.vehicle$.subscribe((vehicleSideNumber) => {
      // Only show the camera when a vehicle is selected
      if (vehicleSideNumber) {
        this.scanner.start();
      } else {
        this.scanner.stop();
      }
    });

    combineLatest([this.data$, this.vehicle$]).subscribe(
      async ([code, vehicleSideNumber]) => {
        if (!vehicleSideNumber) {
          this.state = "VEHICLE_NOT_SELECTED";
          return;
        }

        if (!VEHICLE_PATTERN.test(vehicleSideNumber)) {
          this.state = "VEHICLE_NOT_FOUND";
          return;
        }

        if (!code) {
          this.state = "WAITING";
          return;
        }

        if (!CODE_PATTERN.test(code)) {
          this.state = "TICKET_NOT_FOUND";
          return;
        }

        this.state = "INSPECTING";

        firstValueFrom(
          this.inspectionApi.inspectTicket({
            code,
            body: { vehicleSideNumber },
          }),
        )
          .then((response) => {
            if (response.status === "valid") {
              this.state = "VALID";
              return;
            }

            switch (response.reason) {
              case "ticket-not-found":
                this.state = "TICKET_NOT_FOUND";
                return;
              case "ticket-not-validated":
                this.state = "TICKET_NOT_VALIDATED";
                return;
              case "ticket-expired":
                this.state = "TICKET_EXPIRED";
                return;
              case "ticket-not-valid-for-vehicle":
                this.state = "TICKET_NOT_VALID_FOR_VEHICLE";
                return;
            }
          })
          .catch((response) => {
            if (
              response?.error?.status === 404 &&
              response?.error?.description === "VEHICLE_NOT_FOUND"
            ) {
              this.state = "VEHICLE_NOT_FOUND";
              return;
            }

            this.state = "UNKNOWN_ERROR";
          });
      },
    );
  }

  public ngOnDestroy(): void {
    this.vehicleSubject.next(null);
    this.vehicleSubject.complete();

    this.dataSubject.next(null);
    this.dataSubject.complete();

    // Stop the webcam
    this.scanner.stop();
    this.scanner.destroy();
  }

  protected set vehicleSideNumber(value: string) {
    this.vehicleSubject.next(value);
    this.dataSubject.next(null); // When changing the vehicle, forget codes from previous vehicle
  }

  protected get message(): Message {
    return this.messageStates[this.state];
  }
}
