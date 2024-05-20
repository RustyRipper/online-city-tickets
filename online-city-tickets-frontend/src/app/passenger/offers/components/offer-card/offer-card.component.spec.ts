import { By } from "@angular/platform-browser";

import { Offer } from "~/passenger/offers/types";
import { mount } from "~/shared/testing";

import { OfferCardComponent } from "./offer-card.component";

const offer = {
  id: 1,
  scope: "single-fare",
  kind: "standard",
  displayNameEn: "Single fare",
  displayNamePl: "Jednorazowy",
  priceGrosze: 100,
} satisfies Offer;

describe(OfferCardComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(OfferCardComponent, {
      inputs: { offer },
    });

    expect(sut).toBeTruthy();
  });

  it("should format offer parts using getters", async () => {
    const { sut, element } = await mount(OfferCardComponent, {
      inputs: { offer },
    });

    expect(sut.offerKind).toBe("Standard");
    expect(element.textContent).toContain("Standard");
    expect(sut.offerName).toBe("Single\nfare");
    expect(element.textContent).toContain("Single\nfare");
    expect(sut.offerPrice).toBe("1.00 zł");
    expect(element.textContent).toContain("1.00 zł");
  });

  it("should link if linked is true", async () => {
    const { sut, debug } = await mount(OfferCardComponent, {
      inputs: { offer, linked: true },
    });

    expect(sut.routerLink).toBe("/passenger/offers/1");
    expect(debug.query(By.css("[href='/passenger/offers/1']"))).toBeTruthy();
  });

  it("should not link if linked is false", async () => {
    const { sut, debug } = await mount(OfferCardComponent, {
      inputs: { offer, linked: false },
    });

    expect(sut.routerLink).toBeNull();
    expect(debug.query(By.css("[href='/passenger/offers/1']"))).toBeFalsy();
  });
});
