import type { AccountDto } from "~/generated/api/models";
import { mount } from "~/shared/testing";

import { AccountSettingsPageComponent } from "./account-settings-page.component";

const account = {
  email: "passenger@tickets.pl",
  fullName: "John Doe",
  type: "passenger",
  walletBalanceGrosze: 100,
  phoneNumber: "123456789",
} satisfies AccountDto;

describe(AccountSettingsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(AccountSettingsPageComponent, {
      resolvedData: { account },
    });

    expect(sut).toBeTruthy();
  });
});
