import { Ticket } from "~/passenger/tickets/model";
import { execute } from "~/shared/testing";
import { MOCK_TICKET } from "~/shared/testing/api-mocks";

import { ticketResolver } from "./ticket.resolver";

const CODE = "VGN3QF3TJA" as const;

describe(ticketResolver.name, () => {
  it("should return the ticket if it exists", async () => {
    const { result, mockHttp } = execute(ticketResolver, {
      params: { code: CODE },
    });
    mockHttp(`/tickets/${CODE}`, MOCK_TICKET);

    expect(await result).toEqual(Ticket.deserialize(MOCK_TICKET));
  });

  it("should return an empty observable if the ticket does not exist", async () => {
    const { result, mockHttp } = execute(ticketResolver, {
      params: { code: CODE },
    });
    mockHttp(`/tickets/${CODE}`, {}, 404);

    expect(await result).toBeNull();
  });
});
