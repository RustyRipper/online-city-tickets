import { mount } from "~/shared/testing";

import { SettingsLinkComponent } from "./settings-link.component";

describe(SettingsLinkComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(SettingsLinkComponent);

    expect(sut).toBeTruthy();
  });
});
