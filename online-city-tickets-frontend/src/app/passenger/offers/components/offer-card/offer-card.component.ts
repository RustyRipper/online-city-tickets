import { Component, Input } from "@angular/core";
import { RouterModule } from "@angular/router";

import { TicketOfferDto } from "../../../../generated/api/models";

@Component({
  selector: "app-offer-card",
  standalone: true,
  imports: [RouterModule],
  templateUrl: "./offer-card.component.html",
  styleUrl: "./offer-card.component.css",
})
export class OfferCardComponent {
  private static readonly currency = "zł";

  @Input({ required: true }) public offer!: TicketOfferDto;
  @Input() public linked: boolean = false;

  protected get routerLink(): string | null {
    return this.linked ? `/passenger/offers/${this.offer.id}` : null;
  }

  protected get offerKind(): string {
    switch (this.offer.kind) {
      case "standard":
        return "Standard";
      case "reduced":
        return "Reduced";
    }
  }

  protected get offerName(): string {
    return this.offer.displayNameEn.replaceAll(" ", "\n");
  }

  protected get offerPrice(): string {
    return `${(this.offer.priceGrosze / 100).toFixed(2)} ${OfferCardComponent.currency}`;
  }
}
