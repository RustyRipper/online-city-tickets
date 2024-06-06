import { WalletService } from "~/passenger/wallet/services/wallet.service";
import { execute } from "~/shared/testing";

import { balanceResolver } from "./balance.resolver";

describe(balanceResolver.name, () => {
  it("should return zero for inspector", async () => {
    const { result } = execute(balanceResolver);

    expect(await result).toBe(0);
  });

  it("should return balance for passenger", async () => {
    const { result, mockHttp } = execute(balanceResolver, {
      injects: (tb) => tb.inject(WalletService).revalidateBalanceGrosze(123),
    });
    mockHttp("/account", { type: "passenger", walletBalanceGrosze: 123 });

    expect(await result).toBe(123);
  });
});
