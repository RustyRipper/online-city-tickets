import { mount } from "~/shared/testing";

import { CardAdditionPageComponent } from "./card-addition-page.component";

describe(CardAdditionPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(CardAdditionPageComponent);

    expect(sut).toBeTruthy();
  });
});
