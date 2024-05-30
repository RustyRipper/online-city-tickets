import { mount } from "~/shared/testing";

import { RegisterPageComponent } from "./register-page.component";

describe(RegisterPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(RegisterPageComponent);

    expect(sut).toBeTruthy();
  });

  it("should allow valid form values", async () => {
    const { sut } = await mount(RegisterPageComponent);

    sut.form.group.setValue({
      fullName: "John Doe",
      email: "passenger@tickets.pl",
      password: "12345678",
      repeat: "12345678",
    });

    expect(sut.form.group.valid).toBeTrue();
  });

  it("should reject invalid form values", async () => {
    const { sut } = await mount(RegisterPageComponent);

    sut.form.group.setValue({
      fullName: "John Doe",
      email: "passenger",
      password: "123",
      repeat: "456",
    });

    expect(sut.form.group.valid).toBeFalse();
  });
});
