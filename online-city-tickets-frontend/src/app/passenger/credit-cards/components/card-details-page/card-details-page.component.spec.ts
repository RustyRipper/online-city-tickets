import { mount } from "~/shared/testing";
import { MOCK_CREDIT_CARD } from "~/shared/testing/api-mocks";

import { CreditCard } from "../../model";
import { CardDetailsPageComponent } from "./card-details-page.component";

describe(CardDetailsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(CardDetailsPageComponent, {
      resolvedData: { card: CreditCard.deserialize(MOCK_CREDIT_CARD) },
    });

    expect(sut).toBeTruthy();
  });
});
