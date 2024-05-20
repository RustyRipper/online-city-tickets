import { mount } from "~/shared/testing";

import { AppComponent } from "./app.component";

describe(AppComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(AppComponent);

    expect(sut).toBeTruthy();
  });
});
