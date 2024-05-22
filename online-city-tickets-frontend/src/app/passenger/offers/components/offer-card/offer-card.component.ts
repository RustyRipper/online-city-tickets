import { Component, Input } from "@angular/core";
import { RouterModule } from "@angular/router";

import type { Offer } from "~/passenger/offers/types";
import { WalletService } from "~/passenger/wallet/services/wallet.service";
import { I18nService } from "~/shared/i81n/i18n.service";

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

  public constructor(private readonly i18n: I18nService) {}

  public get routerLink(): string | null {
    return this.linked ? `/passenger/offers/${this.offer.id}` : null;
  }

  public get offerKind(): string {
    switch (this.offer.kind) {
      case "standard":
        return this.i18n.t("offer-card.standard");
      case "reduced":
        return this.i18n.t("offer-card.reduced");
    }
  }

  public get offerName(): string {
    const translated = (() => {
      switch (this.i18n.language) {
        case "en-US":
          return this.offer.displayNameEn;
        case "pl-PL":
          return this.offer.displayNamePl;
      }
    })();

    return translated.replaceAll(" ", "\n");
  }

  public get offerPrice(): string {
    return `${(this.offer.priceGrosze / 100).toFixed(2)} ${WalletService.currency}`;
  }
}
