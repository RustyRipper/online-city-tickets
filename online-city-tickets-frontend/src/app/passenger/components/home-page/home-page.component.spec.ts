import { mount } from "~/shared/testing";

import { HomePageComponent } from "./home-page.component";

describe(HomePageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(HomePageComponent);

    expect(sut).toBeTruthy();
  });
});
