import { mount } from "~/shared/testing/mount";

import { SettingsPageComponent } from "./settings-page.component";

describe(SettingsPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(SettingsPageComponent);

    expect(component).toBeTruthy();
  });
});
