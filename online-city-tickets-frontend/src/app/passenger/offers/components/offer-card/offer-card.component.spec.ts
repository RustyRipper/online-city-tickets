import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterModule } from "@angular/router";

import { OfferCardComponent } from "./offer-card.component";

describe("OfferCardComponent", () => {
  let component: OfferCardComponent;
  let fixture: ComponentFixture<OfferCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfferCardComponent, RouterModule.forRoot([])],
    }).compileComponents();

    fixture = TestBed.createComponent(OfferCardComponent);
    component = fixture.componentInstance;
    component.offer = {
      id: 1,
      scope: "single-fare",
      kind: "standard",
      displayNameEn: "Single fare",
      displayNamePl: "Jednorazowy",
      priceGrosze: 100,
    };
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
