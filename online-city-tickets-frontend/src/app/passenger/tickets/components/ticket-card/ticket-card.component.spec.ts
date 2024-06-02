import { mount } from "~/shared/testing";

import { TicketCardComponent } from "./ticket-card.component";

describe(TicketCardComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(TicketCardComponent);

    expect(sut).toBeTruthy();
  });
});
