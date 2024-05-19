import { mount } from "~/shared/testing/mount";

import { LoginPageComponent } from "./login-page.component";

describe(LoginPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(LoginPageComponent);

    expect(component).toBeTruthy();
  });

  it("should allow valid form values", async () => {
    const { component } = await mount(LoginPageComponent);

    component.form.setValue({
      email: "passenger@tickets.pl",
      password: "12345678",
    });

    expect(component.form.valid).toBeTrue();
  });

  it("should reject invalid form values", async () => {
    const { component } = await mount(LoginPageComponent);

    component.form.setValue({
      email: "",
      password: "",
    });

    expect(component.form.valid).toBeFalse();
  });
});
