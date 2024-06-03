import { execute } from "~/shared/testing";
import { MOCK_PASSENGER } from "~/shared/testing/api-mocks";

import { accountResolver } from "./account.resolver";

describe(accountResolver.name, () => {
  it("should be created", async () => {
    const { result, mockHttp } = execute(accountResolver);
    mockHttp("/account", MOCK_PASSENGER);

    expect(await result).toEqual(MOCK_PASSENGER);
  });
});
