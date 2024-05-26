import { execute } from "~/shared/testing";

import { WalletService } from "../services/wallet.service";
import { balanceResolver } from "./balance.resolver";

describe(balanceResolver.name, () => {
  it("should return zero for inspector", async () => {
    const { result } = execute(balanceResolver);

    expect(await result).toBe(0);
  });

  it("should return balance for passenger", async () => {
    const { result, mockHttp } = execute(balanceResolver, {
      injects: (tb) => tb.inject(WalletService).revalidate(123),
    });
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 123 });

    expect(await result).toBe(123);
  });
});
