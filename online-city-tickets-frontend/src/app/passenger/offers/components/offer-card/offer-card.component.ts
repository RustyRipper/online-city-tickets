import { Component, Input } from "@angular/core";
import { RouterModule } from "@angular/router";

import type { Offer } from "~/passenger/offers/types";
import { WalletService } from "~/passenger/wallet/services/wallet.service";

@Component({
  selector: "app-offer-card",
  standalone: true,
  imports: [RouterModule],
  templateUrl: "./offer-card.component.html",
  styleUrl: "./offer-card.component.css",
})
export class OfferCardComponent {
  @Input({ required: true }) public offer!: Offer;
  @Input() public linked: boolean = false;

  public get routerLink(): string | null {
    return this.linked ? `/passenger/offers/${this.offer.id}` : null;
  }

  public get offerKind(): string {
    switch (this.offer.kind) {
      case "standard":
        return "Standard";
      case "reduced":
        return "Reduced";
    }
  }

  public get offerName(): string {
    return this.offer.displayNameEn.replaceAll(" ", "\n");
  }

  public get offerPrice(): string {
    return `${(this.offer.priceGrosze / 100).toFixed(2)} ${WalletService.currency}`;
  }
}
