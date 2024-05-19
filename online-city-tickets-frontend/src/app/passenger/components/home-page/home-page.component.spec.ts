import { mount } from "~/shared/testing/mount";

import { HomePageComponent } from "./home-page.component";

describe(HomePageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(HomePageComponent);

    expect(component).toBeTruthy();
  });
});
