import type { AccountDto } from "~/generated/api/models";
import { execute } from "~/shared/testing";

import { accountResolver } from "./account.resolver";

const account = {
  email: "passenger@tickets.pl",
  fullName: "John Doe",
  type: "passenger",
  walletBalanceGrosze: 100,
  phoneNumber: "123456789",
} satisfies AccountDto;

describe(accountResolver.name, () => {
  it("should be created", async () => {
    const { result, mockHttp } = execute(accountResolver);
    mockHttp("/account", account);

    expect(await result).toBe(account);
  });
});
