import type { Offer } from "~/passenger/offers/types";
import { execute } from "~/shared/testing";

import { offerResolver } from "./offer.resolver";

const offer = {
  id: 1,
  scope: "single-fare",
  kind: "standard",
  displayNameEn: "Single fare",
  displayNamePl: "Jednorazowy",
  priceGrosze: 100,
} satisfies Offer;

describe(offerResolver.name, () => {
  it("should return the offer if it exists", async () => {
    const { result, mockHttp } = execute(offerResolver, {
      params: { id: "123" },
    });
    mockHttp("/offers/123", offer);

    expect(await result).toBe(offer);
  });

  it("should return an empty observable if the offer does not exist", async () => {
    const { result, mockHttp } = execute(offerResolver, {
      params: { id: "123" },
    });
    mockHttp("/offers/123", {}, 404);

    expect(await result).toBeNull();
  });
});
