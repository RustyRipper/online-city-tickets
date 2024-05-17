import { inject } from "@angular/core";
import { type ResolveFn, Router } from "@angular/router";

import type { TicketOfferDto } from "../../../generated/api/models";
import { OffersApi } from "../../../generated/api/services";
import { EMPTY, catchError } from "rxjs";

export const offerResolver: ResolveFn<TicketOfferDto> = (route, state) => {
  const router = inject(Router);
  const offersApi = inject(OffersApi);

  const id = route.paramMap.get("id") ?? "";
  return offersApi.getTicketOffer({ id }).pipe(
    catchError(() => {
      router.navigateByUrl("/passenger/offers");
      return EMPTY;
    }),
  );
};
