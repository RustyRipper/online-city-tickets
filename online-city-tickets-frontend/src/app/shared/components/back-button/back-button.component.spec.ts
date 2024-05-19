import { mount } from "~/shared/testing/mount";

import { BackButtonComponent } from "./back-button.component";

describe(BackButtonComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(BackButtonComponent);

    expect(component).toBeTruthy();
  });
});
