import { mount } from "~/shared/testing/mount";

import { RegisterPageComponent } from "./register-page.component";

describe(RegisterPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(RegisterPageComponent);

    expect(component).toBeTruthy();
  });

  it("should allow valid form values", async () => {
    const { component } = await mount(RegisterPageComponent);

    component.form.setValue({
      fullName: "John Doe",
      email: "passenger@tickets.pl",
      password: "12345678",
      repeat: "12345678",
    });

    expect(component.form.valid).toBeTrue();
  });

  it("should reject invalid form values", async () => {
    const { component } = await mount(RegisterPageComponent);

    component.form.setValue({
      fullName: "John Doe",
      email: "passenger",
      password: "123",
      repeat: "456",
    });

    expect(component.form.valid).toBeFalse();
  });
});
