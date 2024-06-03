import { By } from "@angular/platform-browser";

import { OfferCardComponent } from "~/passenger/offers/components/offer-card/offer-card.component";
import { Offer } from "~/passenger/offers/types";
import { mount } from "~/shared/testing";
import { MOCK_OFFER } from "~/shared/testing/api-mocks";

import { OfferListPageComponent } from "./offer-list-page.component";

const offers = [
  MOCK_OFFER,
  {
    id: 2,
    kind: "standard",
    scope: "time-limited",
    displayNameEn: "24 hours",
    displayNamePl: "24 godziny",
    durationMinutes: 24 * 60,
    priceGrosze: 200,
  },
  {
    id: 3,
    kind: "reduced",
    scope: "single-fare",
    displayNameEn: "Single fare",
    displayNamePl: "Jednorazowy",
    priceGrosze: 50,
  },
] satisfies Offer[];

describe(OfferListPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(OfferListPageComponent);

    expect(sut).toBeTruthy();
  });

  it("should download offers from the API", async () => {
    const { debug, mockHttp } = await mount(OfferListPageComponent);
    mockHttp("/offers", offers);

    expect(debug.queryAll(By.directive(OfferCardComponent)).length).toBe(
      offers.filter((o) => o.kind === "standard").length,
    );
  });
});
