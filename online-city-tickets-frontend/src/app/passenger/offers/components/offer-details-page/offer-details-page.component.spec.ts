import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ActivatedRoute, RouterModule } from "@angular/router";

import { OfferDetailsPageComponent } from "./offer-details-page.component";

describe("OfferDetailsPageComponent", () => {
  let component: OfferDetailsPageComponent;
  let fixture: ComponentFixture<OfferDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfferDetailsPageComponent, RouterModule.forRoot([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              data: {
                offer: {
                  id: 1,
                  scope: "single-fare",
                  kind: "standard",
                  displayNameEn: "Single fare",
                  displayNamePl: "Jednorazowy",
                  priceGrosze: 100,
                },
                balance: 0,
              },
            },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(OfferDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
