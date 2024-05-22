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
import { AuthService } from "~/shared/auth/services/auth.service";

@Injectable({
  providedIn: "root",
})
export class WalletService {
  public static readonly currency = "zł";

  private readonly balanceGroszeSubject = new BehaviorSubject<number>(0);
  public readonly balanceGrosze$ = this.balanceGroszeSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  public constructor(private readonly accountsApi: AccountsApi) {}

  public revalidate(optimistic?: number): void {
    if (optimistic) {
      this.balanceGroszeSubject.next(optimistic);
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
