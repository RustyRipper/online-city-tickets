import { CommonModule } from "@angular/common";
import { Component, type OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { SelectButtonModule } from "primeng/selectbutton";

import { OfferCardComponent } from "~/passenger/offers/components/offer-card/offer-card.component";
import { OffersService } from "~/passenger/offers/services/offers.service";
import { Offer } from "~/passenger/offers/types";
import { I18nService } from "~/shared/i81n/i18n.service";

type OfferKindItem = { label: string; value: Offer["kind"] };
type OfferGroup = {
  [s in Offer["scope"]]: {
    label: string;
    scope: s;
    offers: Offer[];
  };
}[Offer["scope"]];
type GroupOrder = Omit<OfferGroup, "offers">[];

@Component({
  selector: "app-offer-list-page",
  standalone: true,
  imports: [CommonModule, FormsModule, SelectButtonModule, OfferCardComponent],
  templateUrl: "./offer-list-page.component.html",
  styleUrl: "./offer-list-page.component.css",
})
export class OfferListPageComponent implements OnInit {
  private offers: Offer[] = [];

  private readonly groupOrder: GroupOrder = [
    {
      scope: "single-fare",
      label: this.i18n.t("offer-list-page.single-fare"),
    },
    {
      scope: "time-limited",
      label: this.i18n.t("offer-list-page.time-limited"),
    },
    {
      scope: "long-term",
      label: this.i18n.t("offer-list-page.long-term"),
    },
  ];

  protected readonly ticketKinds: OfferKindItem[] = [
    { label: this.i18n.t("offer-list-page.standard"), value: "standard" },
    { label: this.i18n.t("offer-list-page.reduced"), value: "reduced" },
  ];

  protected readonly kindCell = this.offersService.preferredKindCell;

  public constructor(
    private readonly offersService: OffersService,
    private readonly i18n: I18nService,
  ) {}

  public ngOnInit(): void {
    this.offersService.offers$.subscribe((v) => (this.offers = v));
    this.offersService.revalidateOffers();
  }

  protected get groups(): OfferGroup[] {
    const offers = this.offers.filter((o) => o.kind === this.kindCell.value);
    return this.groupOrder
      .map((group) => ({
        ...group,
        offers: offers
          .filter((offer) => offer.scope === group.scope)
          .sort((a, b) => a.priceGrosze - b.priceGrosze),
      }))
      .filter((group) => group.offers.length > 0);
  }
}
