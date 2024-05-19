import { inject } from "@angular/core";
import { type ResolveFn, Router } from "@angular/router";
import { EMPTY, catchError } from "rxjs";

import { OffersApi } from "~/generated/api/services";
import type { Offer } from "~/passenger/offers/types";

export const offerResolver: ResolveFn<Offer> = (route, _state) => {
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
