import { mount } from "~/shared/testing/mount";

import { WalletIndicatorComponent } from "./wallet-indicator.component";

describe(WalletIndicatorComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(WalletIndicatorComponent);

    expect(component).toBeTruthy();
  });
});
