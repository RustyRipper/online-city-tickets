import { mount } from "~/shared/testing";
import { MOCK_PASSENGER } from "~/shared/testing/api-mocks";

import { AccountSettingsPageComponent } from "./account-settings-page.component";

describe(AccountSettingsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(AccountSettingsPageComponent, {
      resolvedData: { account: MOCK_PASSENGER },
    });

    expect(sut).toBeTruthy();
  });
});
