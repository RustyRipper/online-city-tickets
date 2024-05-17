import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterModule } from "@angular/router";
import { HttpClientModule } from "@angular/common/http";

import { RegisterPageComponent } from "./register-page.component";

describe("RegisterPageComponent", () => {
  let component: RegisterPageComponent;
  let fixture: ComponentFixture<RegisterPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RegisterPageComponent,
        RouterModule.forRoot([]),
        HttpClientModule,
      ],
      providers: [{ provide: Storage, useValue: sessionStorage }],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
