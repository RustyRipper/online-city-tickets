import { ComponentFixture, TestBed } from "@angular/core/testing";

import { WalletIndicatorComponent } from "./wallet-indicator.component";

describe("WalletIndicatorComponent", () => {
  let component: WalletIndicatorComponent;
  let fixture: ComponentFixture<WalletIndicatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WalletIndicatorComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(WalletIndicatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});