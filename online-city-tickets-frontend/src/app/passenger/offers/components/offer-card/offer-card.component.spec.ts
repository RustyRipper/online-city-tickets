import { By } from "@angular/platform-browser";

import { mount } from "~/shared/testing";
import { MOCK_OFFER } from "~/shared/testing/api-mocks";

import { OfferCardComponent } from "./offer-card.component";

describe(OfferCardComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(OfferCardComponent, {
      inputs: { offer: MOCK_OFFER },
    });

    expect(sut).toBeTruthy();
  });

  it("should format offer parts using getters", async () => {
    const { sut, element } = await mount(OfferCardComponent, {
      inputs: { offer: MOCK_OFFER },
    });

    expect(sut.offerKind).toBe("Standard");
    expect(element.textContent).toContain("Standard");
    expect(sut.offerName).toBe("Single\nfare");
    expect(element.textContent).toContain("Single\nfare");
    expect(element.textContent).toContain("1.00");
  });

  it("should link if linked is true", async () => {
    const { sut, debug } = await mount(OfferCardComponent, {
      inputs: { offer: MOCK_OFFER, linked: true },
    });

    expect(sut.routerLink).toBe("/passenger/offers/1");
    expect(debug.query(By.css("[href='/passenger/offers/1']"))).toBeTruthy();
  });

  it("should not link if linked is false", async () => {
    const { sut, debug } = await mount(OfferCardComponent, {
      inputs: { offer: MOCK_OFFER, linked: false },
    });

    expect(sut.routerLink).toBeNull();
    expect(debug.query(By.css("[href='/passenger/offers/1']"))).toBeFalsy();
  });
});
