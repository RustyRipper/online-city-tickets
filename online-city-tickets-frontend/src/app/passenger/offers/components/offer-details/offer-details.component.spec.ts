import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ActivatedRoute, RouterModule } from "@angular/router";

import { OfferDetailsComponent } from "./offer-details.component";

describe("OfferDetailsComponent", () => {
  let component: OfferDetailsComponent;
  let fixture: ComponentFixture<OfferDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfferDetailsComponent, RouterModule.forRoot([])],
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

    fixture = TestBed.createComponent(OfferDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
