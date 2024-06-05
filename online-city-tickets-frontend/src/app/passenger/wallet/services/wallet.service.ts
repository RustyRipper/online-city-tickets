import { Injectable } from "@angular/core";
import {
  BehaviorSubject,
  EMPTY,
  catchError,
  distinctUntilChanged,
  mergeMap,
  of,
} from "rxjs";

import { AccountsApi } from "~/generated/api/services";

@Injectable({
  providedIn: "root",
})
export class WalletService {
  private readonly balanceGroszeSubject = new BehaviorSubject<number>(0);
  public readonly balanceGrosze$ = this.balanceGroszeSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  public constructor(private readonly accountsApi: AccountsApi) {}

  public revalidateBalanceGrosze(optimisticValue?: number): void {
    if (optimisticValue) {
      this.balanceGroszeSubject.next(optimisticValue);
    }
    this.accountsApi
      .getAccount()
      .pipe(
        mergeMap((account) => {
          return account.type === "passenger"
            ? of(account.walletBalanceGrosze)
            : EMPTY;
        }),
        catchError(() => EMPTY),
      )
      .subscribe((v) => this.balanceGroszeSubject.next(v));
  }
}
