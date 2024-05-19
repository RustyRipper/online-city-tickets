import { Offer } from "~/passenger/offers/types";
import { mount } from "~/shared/testing/mount";

import { OfferCardComponent } from "./offer-card.component";

const offer = {
  id: 1,
  scope: "single-fare",
  kind: "standard",
  displayNameEn: "Single fare",
  displayNamePl: "Jednorazowy",
  priceGrosze: 100,
} satisfies Offer;

describe(OfferCardComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(OfferCardComponent, {
      inputs: { offer },
    });

    expect(component).toBeTruthy();
  });
});
