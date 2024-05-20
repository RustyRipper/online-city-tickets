import { mount } from "~/shared/testing";

import { TopBarComponent } from "./top-bar.component";

describe(TopBarComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(TopBarComponent);

    expect(sut).toBeTruthy();
  });
});
