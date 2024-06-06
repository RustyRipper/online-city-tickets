import { firstValueFrom } from "rxjs";

import { provide } from "~/shared/testing";

import { WalletService } from "./wallet.service";

describe(WalletService.name, () => {
  it("should initially have balance of 0", async () => {
    const { sut } = provide(WalletService);

    expect(await firstValueFrom(sut.balanceGrosze$)).toBe(0);
  });

  it("should fetch new balance on revalidation", async () => {
    const { sut, mockHttp } = provide(WalletService);

    sut.revalidateBalanceGrosze();
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 100 });

    expect(await firstValueFrom(sut.balanceGrosze$)).toBe(100);
  });

  it("should ignore balance updates from inspector", async () => {
    const { sut, mockHttp } = provide(WalletService);
    sut.revalidateBalanceGrosze();
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 200 });

    sut.revalidateBalanceGrosze();
    mockHttp("/account", { type: "inspector" });

    expect(await firstValueFrom(sut.balanceGrosze$)).toBe(200);
  });

  it("should perform optimistic revalidation", () => {
    const { sut, mockHttp } = provide(WalletService);
    const balances: number[] = [];
    sut.balanceGrosze$.subscribe((b) => balances.push(b));

    sut.revalidateBalanceGrosze(123);
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 456 });

    expect(balances).toEqual([0, 123, 456]);
  });

  it("should not produce duplicate balance updates", async () => {
    const { sut, mockHttp } = provide(WalletService);
    const balances: number[] = [];
    sut.balanceGrosze$.subscribe((b) => balances.push(b));

    sut.revalidateBalanceGrosze(0);
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 0 });

    expect(balances).toEqual([0]);
  });
});
