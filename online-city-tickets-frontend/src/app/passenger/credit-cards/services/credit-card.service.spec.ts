import { provide } from "~/shared/testing";

import { CreditCardService } from "./credit-card.service";

describe(CreditCardService.name, () => {
  it("should be created", () => {
    const { sut } = provide(CreditCardService);

    expect(sut).toBeTruthy();
  });
});
