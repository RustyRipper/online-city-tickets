import { By } from "@angular/platform-browser";

import { Offer } from "~/passenger/offers/types";
import { mount } from "~/shared/testing/mount";

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
    const { component } = await mount(OfferCardComponent, {
      inputs: { offer },
    });

    expect(component).toBeTruthy();
  });

  it("should format offer parts using getters", async () => {
    const { component, element } = await mount(OfferCardComponent, {
      inputs: { offer },
    });

    expect(component.offerKind).toBe("Standard");
    expect(element.textContent).toContain("Standard");
    expect(component.offerName).toBe("Single\nfare");
    expect(element.textContent).toContain("Single\nfare");
    expect(component.offerPrice).toBe("1.00 zł");
    expect(element.textContent).toContain("1.00 zł");
  });

  it("should link if linked is true", async () => {
    const { component, debug } = await mount(OfferCardComponent, {
      inputs: { offer, linked: true },
    });

    expect(component.routerLink).toBe("/passenger/offers/1");
    expect(debug.query(By.css("[href='/passenger/offers/1']"))).toBeTruthy();
  });

  it("should not link if linked is false", async () => {
    const { component, debug } = await mount(OfferCardComponent, {
      inputs: { offer, linked: false },
    });

    expect(component.routerLink).toBeNull();
    expect(debug.query(By.css("[href='/passenger/offers/1']"))).toBeFalsy();
  });
});
