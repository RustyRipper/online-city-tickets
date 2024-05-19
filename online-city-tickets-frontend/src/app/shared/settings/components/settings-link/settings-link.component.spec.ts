import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterModule } from "@angular/router";

import { SettingsLinkComponent } from "./settings-link.component";

describe("SettingsLinkComponent", () => {
  let component: SettingsLinkComponent;
  let fixture: ComponentFixture<SettingsLinkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SettingsLinkComponent, RouterModule.forRoot([])],
    }).compileComponents();

    fixture = TestBed.createComponent(SettingsLinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
