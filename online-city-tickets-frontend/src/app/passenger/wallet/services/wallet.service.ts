import { Injectable } from "@angular/core";
import {
  BehaviorSubject,
  EMPTY,
  catchError,
  distinctUntilChanged,
  firstValueFrom,
  mergeMap,
  of,
} from "rxjs";

import { AccountsApi, RechargingApi } from "~/generated/api/services";

@Injectable({
  providedIn: "root",
})
export class WalletService {
  private readonly balanceGroszeSubject = new BehaviorSubject<number>(0);
  public readonly balanceGrosze$ = this.balanceGroszeSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  public constructor(
    private readonly accountsApi: AccountsApi,
    private readonly rechargingApi: RechargingApi,
  ) {}

  public async rechargeWithCard(
    amountGrosze: number,
    creditCardId: number,
    cvc: string,
  ): Promise<void> {
    const { newWalletBalanceGrosze } = await firstValueFrom(
      this.rechargingApi.rechargeWithSavedCreditCard({
        body: { amountGrosze, creditCardId, cvc },
      }),
    );
    this.balanceGroszeSubject.next(newWalletBalanceGrosze);
  }

  public async rechargeWithBlik(
    amountGrosze: number,
    blikCode: string,
  ): Promise<void> {
    const { newWalletBalanceGrosze } = await firstValueFrom(
      this.rechargingApi.rechargeWithBlik({ body: { amountGrosze, blikCode } }),
    );
    this.balanceGroszeSubject.next(newWalletBalanceGrosze);
  }

  public revalidateBalanceGrosze(): void {
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
