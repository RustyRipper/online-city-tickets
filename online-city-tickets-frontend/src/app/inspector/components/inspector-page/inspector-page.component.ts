import { AfterViewInit, Component, ElementRef, ViewChild } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { InputMaskModule } from "primeng/inputmask";
import { MessagesModule } from "primeng/messages";
import QrScanner from "qr-scanner";

import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";
import { SettingsLinkComponent } from "~/shared/settings/components/settings-link/settings-link.component";

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
export class InspectorPageComponent implements AfterViewInit {
  protected vehicleSideNumber: string = "";

  @ViewChild("video") protected video!: ElementRef<HTMLVideoElement>;
  protected scanner!: QrScanner;

  public constructor(protected readonly i18n: I18nService) {}

  public ngAfterViewInit(): void {
    this.scanner = new QrScanner(
      this.video.nativeElement,
      ({ data }) => this.onScan(data),
      { returnDetailedScanResult: true },
    );
  }

  protected onVehicleChange(): void {
    if (this.vehicleSideNumber.replaceAll("_", "").length === 9) {
      this.scanner.start();
    } else {
      this.scanner.stop();
    }
  }

  protected onScan(data: string) {
    console.log(data);
  }
}
