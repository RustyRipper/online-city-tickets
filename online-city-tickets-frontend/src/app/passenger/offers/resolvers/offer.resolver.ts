import { inject } from "@angular/core";
import { ResolveFn, Router } from "@angular/router";

import { TicketOfferDto } from "../../../api/models";
import { OffersApi } from "../../../api/services";
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
