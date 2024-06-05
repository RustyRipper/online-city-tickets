import { Component, Input } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";

import { Ticket } from "~/passenger/tickets/model";
import { I18nService } from "~/shared/i81n/i18n.service";

@Component({
  selector: "app-ticket-card",
  standalone: true,
  imports: [RouterModule, ButtonModule],
  templateUrl: "./ticket-card.component.html",
  styleUrl: "./ticket-card.component.css",
})
export class TicketCardComponent {
  @Input({ required: true }) public ticket!: Ticket;

  public constructor(protected readonly i18n: I18nService) {}

  protected get ticketKind(): string {
    return this.i18n.t(`ticket-card.${this.ticket.offer.kind}`);
  }

  protected get ticketName(): string {
    const translated = (() => {
      switch (this.i18n.language) {
        case "en-US":
          return this.ticket.offer.displayNameEn;
        case "pl-PL":
          return this.ticket.offer.displayNamePl;
      }
    })();

    return translated.replaceAll(" ", "\n");
  }
}
