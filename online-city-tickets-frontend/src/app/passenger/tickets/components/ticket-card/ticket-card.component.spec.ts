import { mount } from "~/shared/testing";
import { MOCK_TICKET } from "~/shared/testing/api-mocks";

import { Ticket } from "../../model";
import { TicketCardComponent } from "./ticket-card.component";

describe(TicketCardComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(TicketCardComponent, {
      inputs: { ticket: Ticket.deserialize(MOCK_TICKET) },
    });

    expect(sut).toBeTruthy();
  });
});
