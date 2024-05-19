import { mount } from "~/shared/testing/mount";

import { OfferListPageComponent } from "./offer-list-page.component";

describe(OfferListPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(OfferListPageComponent);

    expect(component).toBeTruthy();
  });
});
