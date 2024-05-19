import { mount } from "~/shared/testing/mount";

import { SettingsLinkComponent } from "./settings-link.component";

describe(SettingsLinkComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(SettingsLinkComponent);

    expect(component).toBeTruthy();
  });
});
