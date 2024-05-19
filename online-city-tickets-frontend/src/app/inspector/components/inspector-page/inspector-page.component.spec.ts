import { mount } from "~/shared/testing/mount";

import { InspectorPageComponent } from "./inspector-page.component";

describe(InspectorPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(InspectorPageComponent);

    expect(component).toBeTruthy();
  });
});
