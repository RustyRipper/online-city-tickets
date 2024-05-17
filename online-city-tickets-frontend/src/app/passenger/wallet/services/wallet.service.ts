import { Injectable } from "@angular/core";
import {
  BehaviorSubject,
  EMPTY,
  type Observable,
  catchError,
  mergeMap,
  of,
} from "rxjs";

import { AccountsApi } from "../../../generated/api/services";
import { AuthService } from "../../../shared/auth/services/auth.service";

@Injectable({
  providedIn: "root",
})
export class WalletService {
  public static readonly currency = "zł";

  private readonly balanceGroszeSubject = new BehaviorSubject<number>(0);

  public constructor(private readonly accountsApi: AccountsApi) {}

  public get balanceGrosze$(): Observable<number> {
    return this.balanceGroszeSubject.asObservable();
  }

  public revalidate(): void {
    this.accountsApi
      .getAccount()
      .pipe(
        mergeMap((account) => {
          // FIXME: remove this once backend is updated
          account = AuthService.fixAccountObject(account);
          return account.type === "passenger"
            ? of(account.walletBalanceGrosze)
            : EMPTY;
        }),
        catchError(() => EMPTY),
      )
      .subscribe((v) => this.balanceGroszeSubject.next(v));
  }
}
