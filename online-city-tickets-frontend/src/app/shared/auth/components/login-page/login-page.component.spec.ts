import { mount } from "~/shared/testing";

import { LoginPageComponent } from "./login-page.component";

describe(LoginPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(LoginPageComponent);

    expect(sut).toBeTruthy();
  });

  it("should allow valid form values", async () => {
    const { sut } = await mount(LoginPageComponent);

    sut.form.group.setValue({
      email: "passenger@tickets.pl",
      password: "12345678",
    });

    expect(sut.form.group.valid).toBeTrue();
  });

  it("should reject invalid form values", async () => {
    const { sut } = await mount(LoginPageComponent);

    sut.form.group.setValue({
      email: "",
      password: "",
    });

    expect(sut.form.group.valid).toBeFalse();
  });
});
