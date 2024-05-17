import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { SelectButtonModule } from "primeng/selectbutton";

import { TicketOfferDto } from "../../../../generated/api/models";
import { OffersApi } from "../../../../generated/api/services";
import { OfferCardComponent } from "../offer-card/offer-card.component";
import { StoredCell } from "../../../../shared/store/stored-cell";

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

  protected readonly kindCell: StoredCell<OfferKind>;

  public constructor(
    private readonly offersApi: OffersApi,
    storage: Storage,
  ) {
    this.kindCell = StoredCell.of(storage, "TICKET_KIND", "standard");
  }

  public ngOnInit(): void {
    this.offersApi
      .listTicketOffers()
      .subscribe((offers) => (this.offers = offers));
  }

  protected get groups(): OfferGroup[] {
    const offers = this.offers.filter((o) => o.kind === this.kindCell.value);
    return GROUP_ORDER.map((g) => ({
      ...g,
      offers: offers
        .filter((o) => o.scope === g.scope)
        .sort((a, b) => a.priceGrosze - b.priceGrosze),
    })).filter((g) => g.offers.length > 0);
  }
}
