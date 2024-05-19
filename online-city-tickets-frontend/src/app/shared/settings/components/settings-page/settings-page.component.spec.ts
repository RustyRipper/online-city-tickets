import { mount } from "~/shared/testing";

import { SettingsPageComponent } from "./settings-page.component";

describe(SettingsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(SettingsPageComponent);

    expect(sut).toBeTruthy();
  });
});
