import { mount } from "~/shared/testing";

import { CardListPageComponent } from "./card-list-page.component";

describe(CardListPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(CardListPageComponent);

    expect(sut).toBeTruthy();
  });
});
