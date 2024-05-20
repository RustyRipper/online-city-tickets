import type { Offer } from "~/passenger/offers/types";
import { mount } from "~/shared/testing";

import { OfferDetailsPageComponent } from "./offer-details-page.component";

const offer = {
  id: 1,
  scope: "single-fare",
  kind: "standard",
  displayNameEn: "Single fare",
  displayNamePl: "Jednorazowy",
  priceGrosze: 100,
} satisfies Offer;

describe(OfferDetailsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(OfferDetailsPageComponent, {
      resolvedData: { offer, balance: 0 },
    });

    expect(sut).toBeTruthy();
  });
});
