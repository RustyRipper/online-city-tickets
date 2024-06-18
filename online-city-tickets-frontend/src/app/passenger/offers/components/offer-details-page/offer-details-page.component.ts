import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";

import { OfferCardComponent } from "~/passenger/offers/components/offer-card/offer-card.component";
import type { Offer } from "~/passenger/offers/types";
import { PaymentSheetComponent } from "~/passenger/payment/components/payment-sheet/payment-sheet.component";
import { TicketsService } from "~/passenger/tickets/services/tickets.service";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i18n/i18n.service";

@Component({
  selector: "app-offer-details-page",
  standalone: true,
  imports: [
    TopBarComponent,
    BackButtonComponent,
    OfferCardComponent,
    PaymentSheetComponent,
  ],
  templateUrl: "./offer-details-page.component.html",
  styleUrl: "./offer-details-page.component.css",
})
export class OfferDetailsPageComponent {
  protected readonly offer: Offer;

  public constructor(
    private readonly ticketsService: TicketsService,
    private readonly router: Router,
    protected readonly i18n: I18nService,
    activatedRoute: ActivatedRoute,
  ) {
    this.offer = activatedRoute.snapshot.data["offer"];
  }

  public async purchase(): Promise<void> {
    const ticket = await this.ticketsService.purchaseTicket(this.offer.id);
    this.router.navigateByUrl(`/passenger/tickets/${ticket.code}`);
  }
}
