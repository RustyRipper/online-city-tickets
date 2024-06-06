import { inject } from "@angular/core";
import { ResolveFn, Router } from "@angular/router";
import { EMPTY, catchError, map } from "rxjs";

import { CardsApi } from "~/generated/api/services";

import { CreditCard } from "../model";

export const creditCardResolver: ResolveFn<CreditCard> = (route, _state) => {
  const router = inject(Router);
  const cardsApi = inject(CardsApi);

  const id = route.paramMap.get("id") ?? "";
  return cardsApi.getCreditCard({ id }).pipe(
    map(CreditCard.deserialize),
    catchError(() => {
      router.navigateByUrl("/passenger/credit-cards");
      return EMPTY;
    }),
  );
};
