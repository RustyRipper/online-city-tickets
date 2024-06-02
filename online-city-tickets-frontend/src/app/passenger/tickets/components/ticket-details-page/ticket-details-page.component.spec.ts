import { mount } from "~/shared/testing";

import { TicketDetailsPageComponent } from "./ticket-details-page.component";

describe(TicketDetailsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(TicketDetailsPageComponent);

    expect(sut).toBeTruthy();
  });
});
