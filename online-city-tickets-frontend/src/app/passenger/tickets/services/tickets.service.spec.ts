import { firstValueFrom } from "rxjs";

import { Ticket } from "~/passenger/tickets/model";
import { provide } from "~/shared/testing";
import { MOCK_TICKET } from "~/shared/testing/api-mocks";

import { TicketsService } from "./tickets.service";

describe(TicketsService.name, () => {
  it("should initially have no tickets", async () => {
    const { sut } = provide(TicketsService);

    expect(await firstValueFrom(sut.tickets$)).toEqual([]);
  });

  it("should fetch new tickets on revalidation", async () => {
    const { sut, mockHttp } = provide(TicketsService);

    sut.revalidateTickets();
    mockHttp("/tickets", [MOCK_TICKET]);

    expect(await firstValueFrom(sut.tickets$)).toEqual([
      Ticket.deserialize(MOCK_TICKET),
    ]);
  });
});
