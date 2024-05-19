import { mount } from "~/shared/testing/mount";

import { WalletIndicatorComponent } from "./wallet-indicator.component";

describe(WalletIndicatorComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(WalletIndicatorComponent);

    expect(component).toBeTruthy();
  });

  it("should display the balance", async () => {
    const { component, element, mockHttp } = await mount(
      WalletIndicatorComponent,
    );
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 12345 });

    expect(component.label).toBe("123.45 zł");
    expect(element.textContent).toContain("123.45 zł");
  });
});
