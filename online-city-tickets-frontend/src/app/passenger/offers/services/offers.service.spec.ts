import { firstValueFrom } from "rxjs";

import { provide } from "~/shared/testing";
import { MOCK_OFFER } from "~/shared/testing/api-mocks";

import { OffersService } from "./offers.service";

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
    mockHttp("/offers", [MOCK_OFFER]);

    expect(await firstValueFrom(sut.offers$)).toEqual([MOCK_OFFER]);
  });
});
