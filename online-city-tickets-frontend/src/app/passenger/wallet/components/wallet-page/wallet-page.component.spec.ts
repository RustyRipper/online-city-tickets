import { mount } from "~/shared/testing/mount";

import { WalletPageComponent } from "./wallet-page.component";

describe(WalletPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(WalletPageComponent);

    expect(component).toBeTruthy();
  });
});
