import { inject } from "@angular/core";
import type { ResolveFn } from "@angular/router";
import { AccountsApi } from "../../../generated/api/services";
import { catchError, map, of } from "rxjs";

export const balanceResolver: ResolveFn<number> = (_route, _state) =>
  inject(AccountsApi)
    .getAccount()
    .pipe(
      map((account) =>
        account.type === "passenger" ? account.walletBalanceGrosze : 0,
      ),
      catchError(() => of(0)),
    );
