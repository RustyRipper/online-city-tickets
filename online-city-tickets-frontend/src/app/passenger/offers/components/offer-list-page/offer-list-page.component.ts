import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { SelectButtonModule } from "primeng/selectbutton";

import { TicketOfferDto } from "~/generated/api/models";
import { OfferCardComponent } from "~/passenger/offers/components/offer-card/offer-card.component";
import { OffersService } from "~/passenger/offers/services/offers.service";

type OfferKind = TicketOfferDto["kind"];
type OfferKindItem = { label: string; value: OfferKind };
type OfferGroup = {
  [s in TicketOfferDto["scope"]]: {
    label: string;
    scope: s;
    offers: TicketOfferDto[];
  };
}[TicketOfferDto["scope"]];

const GROUP_ORDER = [
  {
    scope: "single-fare",
    label: "Single fare",
  },
  {
    scope: "time-limited",
    label: "Time limited",
  },
  {
    scope: "long-term",
    label: "Long term",
  },
] as const;

@Component({
  selector: "app-offer-list-page",
  standalone: true,
  imports: [CommonModule, FormsModule, SelectButtonModule, OfferCardComponent],
  templateUrl: "./offer-list-page.component.html",
  styleUrl: "./offer-list-page.component.css",
})
export class OfferListPageComponent implements OnInit {
  private offers: TicketOfferDto[] = [];

  protected readonly ticketKinds: OfferKindItem[] = [
    { label: "Standard", value: "standard" },
    { label: "Reduced", value: "reduced" },
  ];

  protected readonly kindCell = this.offersService.preferredKindCell;

  public constructor(private readonly offersService: OffersService) {}

  public ngOnInit(): void {
    this.offersService.offers$.subscribe((v) => (this.offers = v));
    this.offersService.revalidateOffers();
  }

  protected get groups(): OfferGroup[] {
    const offers = this.offers.filter((o) => o.kind === this.kindCell.value);
    return GROUP_ORDER.map((group) => ({
      ...group,
      offers: offers
        .filter((offer) => offer.scope === group.scope)
        .sort((a, b) => a.priceGrosze - b.priceGrosze),
    })).filter((group) => group.offers.length > 0);
  }
}
