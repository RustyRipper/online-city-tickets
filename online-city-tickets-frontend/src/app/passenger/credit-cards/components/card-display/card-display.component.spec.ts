import { mount } from "~/shared/testing";

import { CardDisplayComponent } from "./card-display.component";

describe(CardDisplayComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(CardDisplayComponent, {
      inputs: { number: "", holderName: "", expirationDate: "" },
    });

    expect(sut).toBeTruthy();
  });

  for (const [provider, digit] of [
    ["mastercard", "2"],
    ["amex", "3"],
    ["visa", "4"],
    ["mastercard", "5"],
    ["discover", "6"],
  ]) {
    it(`should detect ${provider} cards starting with ${digit}`, async () => {
      const { element } = await mount(CardDisplayComponent, {
        inputs: { number: digit, holderName: "", expirationDate: "" },
      });

      expect(
        element.querySelector(`[data-provider='${provider}']`),
      ).toBeTruthy();
    });
  }
});
