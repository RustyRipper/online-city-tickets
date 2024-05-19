import { mount } from "~/shared/testing";

import { WalletIndicatorComponent } from "./wallet-indicator.component";

describe(WalletIndicatorComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(WalletIndicatorComponent);

    expect(sut).toBeTruthy();
  });

  it("should display the balance", async () => {
    const { sut, element, mockHttp } = await mount(WalletIndicatorComponent);
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 12345 });

    expect(sut.label).toBe("123.45 zł");
    expect(element.textContent).toContain("123.45 zł");
  });
});
