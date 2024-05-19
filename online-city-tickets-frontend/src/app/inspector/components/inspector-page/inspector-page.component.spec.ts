import { mount } from "~/shared/testing";

import { InspectorPageComponent } from "./inspector-page.component";

describe(InspectorPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(InspectorPageComponent);

    expect(sut).toBeTruthy();
  });
});
