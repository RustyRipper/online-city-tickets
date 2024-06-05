import { execute } from "~/shared/testing";
import { MOCK_CREDIT_CARD } from "~/shared/testing/api-mocks";

import { CreditCard } from "../model";
import { creditCardResolver } from "./credit-card.resolver";

describe(creditCardResolver.name, () => {
  it("should return the card if it exists", async () => {
    const { result, mockHttp } = execute(creditCardResolver, {
      params: { id: "1" },
    });
    mockHttp("/credit-cards/1", MOCK_CREDIT_CARD);

    expect(await result).toEqual(CreditCard.deserialize(MOCK_CREDIT_CARD));
  });

  it("should return an empty observable if the card does not exist", async () => {
    const { result, mockHttp } = execute(creditCardResolver, {
      params: { id: "1" },
    });
    mockHttp("/credit-cards/1", {}, 404);

    expect(await result).toBeNull();
  });
});
