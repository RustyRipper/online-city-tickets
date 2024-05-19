import { mount } from "~/shared/testing/mount";

import { RegisterPageComponent } from "./register-page.component";

describe(RegisterPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(RegisterPageComponent);

    expect(component).toBeTruthy();
  });
});
