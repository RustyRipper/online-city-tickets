import { QRCodeSVG } from "@akamfoad/qrcode";
import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { TagModule } from "primeng/tag";

import type { PassengerDto } from "~/generated/api/models";
import { Ticket } from "~/passenger/tickets/model";
import { WalletService } from "~/passenger/wallet/services/wallet.service";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";

@Component({
  selector: "app-ticket-details-page",
  standalone: true,
  imports: [TagModule, TopBarComponent, BackButtonComponent],
  templateUrl: "./ticket-details-page.component.html",
  styleUrl: "./ticket-details-page.component.css",
})
export class TicketDetailsPageComponent {
  protected readonly ticket: Ticket;
  protected readonly account: PassengerDto;

  public constructor(
    protected readonly i18n: I18nService,
    activatedRoute: ActivatedRoute,
  ) {
    this.ticket = activatedRoute.snapshot.data["ticket"];
    this.account = activatedRoute.snapshot.data["account"];
  }

  protected get title(): string {
    const scope = (() => {
      switch (this.i18n.language) {
        case "en-US":
          return this.ticket.offer.displayNameEn;
        case "pl-PL":
          return this.ticket.offer.displayNamePl;
      }
    })();
    const kind = this.i18n.t(`ticket-details-page.${this.ticket.offer.kind}`);
    return `${scope}, ${kind}`;
  }

  protected get warning(): string | null {
    if (this.ticket.status === "active") {
      return null;
    }
    return this.i18n.t(`ticket-details-page.${this.ticket.status}`);
  }

  protected get codeDataUrl(): string {
    const qrCode = new QRCodeSVG(this.ticket.code, { level: "H" });
    return qrCode.toDataUrl()!;
  }

  protected get ticketPrice(): string {
    return `${(this.ticket.offer.priceGrosze / 100).toFixed(2)} ${WalletService.currency}`;
  }
}
