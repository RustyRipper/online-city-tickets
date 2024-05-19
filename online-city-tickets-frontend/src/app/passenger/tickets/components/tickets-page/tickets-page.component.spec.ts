import { mount } from "~/shared/testing/mount";

import { TicketsPageComponent } from "./tickets-page.component";

describe(TicketsPageComponent.name, () => {
  it("should mount", async () => {
    const { component } = await mount(TicketsPageComponent);

    expect(component).toBeTruthy();
  });
});
