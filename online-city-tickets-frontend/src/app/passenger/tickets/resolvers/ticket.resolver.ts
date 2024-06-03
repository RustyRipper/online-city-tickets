import { inject } from "@angular/core";
import { ResolveFn, Router } from "@angular/router";
import { EMPTY, catchError, map } from "rxjs";

import { TicketsApi } from "~/generated/api/services";
import { Ticket } from "~/passenger/tickets/model";

export const ticketResolver: ResolveFn<Ticket> = (route, _state) => {
  const router = inject(Router);
  const ticketsApi = inject(TicketsApi);

  const code = route.paramMap.get("code") ?? "";
  return ticketsApi.getTicket({ code }).pipe(
    map(Ticket.deserialize),
    catchError(() => {
      router.navigateByUrl("/passenger/tickets");
      return EMPTY;
    }),
  );
};
