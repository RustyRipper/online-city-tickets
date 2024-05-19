import { firstValueFrom } from "rxjs";

import type { Offer } from "~/passenger/offers/types";
import { provide } from "~/shared/testing";

import { OffersService } from "./offers.service";

const offer = {
  id: 1,
  scope: "single-fare",
  kind: "standard",
  displayNameEn: "Single fare",
  displayNamePl: "Jednorazowy",
  priceGrosze: 100,
} satisfies Offer;

describe(OffersService.name, () => {
  it("should read preferred ticket offer kind from storage", () => {
    const { sut } = provide(OffersService, {
      storage: {
        TICKET_KIND: JSON.stringify("reduced"),
      },
    });

    expect(sut.preferredKindCell.value).toBe("reduced");
  });

  it("should initially have no offers", async () => {
    const { sut } = provide(OffersService);

    expect(await firstValueFrom(sut.offers$)).toEqual([]);
  });

  it("should fetch new offers on revalidation", async () => {
    const { sut, mockHttp } = provide(OffersService);

    sut.revalidateOffers();
    mockHttp("/offers", [offer]);

    expect(await firstValueFrom(sut.offers$)).toEqual([offer]);
  });
});
