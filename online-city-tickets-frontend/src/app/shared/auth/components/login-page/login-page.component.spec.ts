import { mount } from "~/shared/testing/mount";

import { LoginPageComponent } from "./login-page.component";

describe(LoginPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(LoginPageComponent);

    expect(component).toBeTruthy();
  });
});
