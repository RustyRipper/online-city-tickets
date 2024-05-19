import { mount } from "~/shared/testing";

import { AccountSettingsPageComponent } from "./account-settings-page.component";

describe(AccountSettingsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(AccountSettingsPageComponent);

    expect(sut).toBeTruthy();
  });
});
