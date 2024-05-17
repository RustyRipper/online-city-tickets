import { ComponentFixture, TestBed } from "@angular/core/testing";
import { HttpClientModule } from "@angular/common/http";

import { OfferListPageComponent } from "./offer-list-page.component";

describe("OfferListPageComponent", () => {
  let component: OfferListPageComponent;
  let fixture: ComponentFixture<OfferListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfferListPageComponent, HttpClientModule],
      providers: [{ provide: Storage, useValue: sessionStorage }],
    }).compileComponents();

    fixture = TestBed.createComponent(OfferListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
