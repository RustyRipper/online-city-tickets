import { mount } from "~/shared/testing";

import { WalletPageComponent } from "./wallet-page.component";

describe(WalletPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(WalletPageComponent, {
      resolvedData: { balance: 0 },
    });

    expect(sut).toBeTruthy();
  });
});
