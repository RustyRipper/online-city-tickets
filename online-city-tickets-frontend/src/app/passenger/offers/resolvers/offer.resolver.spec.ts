import { execute } from "~/shared/testing";
import { MOCK_OFFER } from "~/shared/testing/api-mocks";

import { offerResolver } from "./offer.resolver";

describe(offerResolver.name, () => {
  it("should return the offer if it exists", async () => {
    const { result, mockHttp } = execute(offerResolver, {
      params: { id: "123" },
    });
    mockHttp("/offers/123", MOCK_OFFER);

    expect(await result).toBe(MOCK_OFFER);
  });

  it("should return an empty observable if the offer does not exist", async () => {
    const { result, mockHttp } = execute(offerResolver, {
      params: { id: "123" },
    });
    mockHttp("/offers/123", {}, 404);

    expect(await result).toBeNull();
  });
});
