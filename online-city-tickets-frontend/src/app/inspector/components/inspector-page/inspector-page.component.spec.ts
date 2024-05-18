import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterModule } from "@angular/router";

import { InspectorPageComponent } from "./inspector-page.component";

describe("InspectorPageComponent", () => {
  let component: InspectorPageComponent;
  let fixture: ComponentFixture<InspectorPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InspectorPageComponent, RouterModule.forRoot([])],
    }).compileComponents();

    fixture = TestBed.createComponent(InspectorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
