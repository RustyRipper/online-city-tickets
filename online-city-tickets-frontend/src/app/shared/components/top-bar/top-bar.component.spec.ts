import { mount } from "~/shared/testing/mount";

import { TopBarComponent } from "./top-bar.component";

describe(TopBarComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(TopBarComponent);

    expect(component).toBeTruthy();
  });
});
