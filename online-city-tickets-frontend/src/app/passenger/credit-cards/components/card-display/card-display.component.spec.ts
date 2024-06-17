import { mount } from "~/shared/testing";

import { CardDisplayComponent } from "./card-display.component";

describe(CardDisplayComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(CardDisplayComponent);

    expect(sut).toBeTruthy();
  });
});
