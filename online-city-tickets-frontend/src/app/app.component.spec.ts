import { mount } from "~/shared/testing/mount";

import { AppComponent } from "./app.component";

describe(AppComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(AppComponent);

    expect(component).toBeTruthy();
  });
});
