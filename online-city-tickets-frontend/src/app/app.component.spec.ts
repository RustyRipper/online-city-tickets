import { mount } from "~/shared/testing";
import { setupDocument } from "~/shared/theme/services/theme.service.spec";

import { AppComponent } from "./app.component";

describe(AppComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(AppComponent, { injects: setupDocument });

    expect(sut).toBeTruthy();
  });
});
