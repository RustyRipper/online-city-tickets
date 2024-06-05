import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { OfferCardComponent } from "~/passenger/offers/components/offer-card/offer-card.component";
import type { Offer } from "~/passenger/offers/types";
import { PaymentSheetComponent } from "~/passenger/payment/components/payment-sheet/payment-sheet.component";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";

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

  public constructor(activatedRoute: ActivatedRoute) {
    this.offer = activatedRoute.snapshot.data["offer"];
  }
}
