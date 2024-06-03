import { mount } from "~/shared/testing";
import { MOCK_PASSENGER, MOCK_TICKET } from "~/shared/testing/api-mocks";

import { Ticket } from "../../model";
import { TicketDetailsPageComponent } from "./ticket-details-page.component";

describe(TicketDetailsPageComponent.name, () => {
  it("should mount", async () => {
    const { sut } = await mount(TicketDetailsPageComponent, {
      resolvedData: {
        ticket: Ticket.deserialize(MOCK_TICKET),
        account: MOCK_PASSENGER,
      },
    });

    expect(sut).toBeTruthy();
  });
});
