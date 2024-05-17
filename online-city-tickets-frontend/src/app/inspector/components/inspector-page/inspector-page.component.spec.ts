import { ComponentFixture, TestBed } from "@angular/core/testing";

import { InspectorPageComponent } from "./inspector-page.component";

describe("InspectorPageComponent", () => {
  let component: InspectorPageComponent;
  let fixture: ComponentFixture<InspectorPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InspectorPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InspectorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
