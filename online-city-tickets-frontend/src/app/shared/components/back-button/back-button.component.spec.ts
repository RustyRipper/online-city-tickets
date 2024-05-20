import { mount } from "~/shared/testing";

import { BackButtonComponent } from "./back-button.component";

describe(BackButtonComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(BackButtonComponent);

    expect(sut).toBeTruthy();
  });
});
