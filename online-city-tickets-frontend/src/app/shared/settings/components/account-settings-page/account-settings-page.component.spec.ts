import { mount } from "~/shared/testing/mount";

import { AccountSettingsPageComponent } from "./account-settings-page.component";

describe(AccountSettingsPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(AccountSettingsPageComponent);

    expect(component).toBeTruthy();
  });
});
