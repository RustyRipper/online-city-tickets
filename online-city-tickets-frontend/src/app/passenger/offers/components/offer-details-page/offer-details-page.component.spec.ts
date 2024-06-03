import { mount } from "~/shared/testing";
import { MOCK_OFFER } from "~/shared/testing/api-mocks";

import { OfferDetailsPageComponent } from "./offer-details-page.component";

describe(OfferDetailsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(OfferDetailsPageComponent, {
      resolvedData: { offer: MOCK_OFFER, balance: 0 },
    });

    expect(sut).toBeTruthy();
  });
});
