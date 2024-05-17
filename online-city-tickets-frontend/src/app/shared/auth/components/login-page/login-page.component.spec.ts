import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterModule } from "@angular/router";
import { HttpClientModule } from "@angular/common/http";

import { LoginPageComponent } from "./login-page.component";

describe("LoginPageComponent", () => {
  let component: LoginPageComponent;
  let fixture: ComponentFixture<LoginPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginPageComponent, RouterModule.forRoot([]), HttpClientModule],
      providers: [{ provide: Storage, useValue: sessionStorage }],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
