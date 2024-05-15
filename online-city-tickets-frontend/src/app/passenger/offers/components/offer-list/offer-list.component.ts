import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SelectButtonModule } from "primeng/selectbutton";

import { TicketOfferDto } from "../../../../api/models";
import { OffersApi } from "../../../../api/services";
import { StoreService } from "../../../../shared/store/store.service";

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
  selector: "app-offer-list",
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, SelectButtonModule],
  templateUrl: "./offer-list.component.html",
  styleUrl: "./offer-list.component.css",
})
export class OfferListComponent implements OnInit {
  private offers: TicketOfferDto[] = [];

  protected readonly ticketKinds: OfferKindItem[] = [
    { label: "Standard", value: "standard" },
    { label: "Reduced", value: "reduced" },
  ];

  protected kind: OfferKind;

  public constructor(
    private readonly offersApi: OffersApi,
    storeService: StoreService,
  ) {
    this.kind = storeService.ticketKind.value;
  }

  public ngOnInit(): void {
    this.offersApi
      .listTicketOffers()
      .subscribe((offers) => (this.offers = offers));
  }

  protected get groups(): OfferGroup[] {
    const offers = this.offers.filter((offer) => offer.kind === this.kind);
    return GROUP_ORDER.map((group) => ({
      ...group,
      offers: offers
        .filter((offer) => offer.scope === group.scope)
        .sort((a, b) => a.priceGrosze - b.priceGrosze),
    })).filter((group) => group.offers.length > 0);
  }

  protected labelOf(kind: OfferKind): string {
    return this.ticketKinds.find((tk) => tk.value === kind)!.label;
  }
}
