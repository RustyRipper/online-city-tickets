import { mount } from "~/shared/testing";

import { TicketsPageComponent } from "./tickets-page.component";

describe(TicketsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(TicketsPageComponent);

    expect(sut).toBeTruthy();
  });
});
