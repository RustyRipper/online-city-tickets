import { TestBed } from "@angular/core/testing";

import { executeResolver } from "~/shared/testing/execute-resolver";

import { WalletService } from "../services/wallet.service";
import { balanceResolver } from "./balance.resolver";

describe(balanceResolver.name, () => {
  it("should return zero for inspector", async () => {
    const { result } = executeResolver(balanceResolver);

    expect(await result).toBe(0);
  });

  it("should return balance for passenger", async () => {
    const { result, mockHttp } = executeResolver(balanceResolver, {
      customInjects: () => TestBed.inject(WalletService).revalidate(123),
    });
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 123 });

    expect(await result).toBe(123);
  });
});
