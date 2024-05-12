import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

import type { Account } from "../types";
import type { LoginReq } from "../../api/models";
import { StoreService } from "../../shared/store/store.service";
import { AccountsApi, AuthApi } from "../../api/services";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private readonly jwtCell;
  private readonly accountCell;

  public constructor(
    private readonly accountsApi: AccountsApi,
    private readonly authApi: AuthApi,
    storeService: StoreService,
  ) {
    this.jwtCell = storeService.jwt;
    this.accountCell = storeService.account;
  }

  public get jwt(): string | null {
    return this.jwtCell.value;
  }

  public get account(): Account | null {
    return this.accountCell.value;
  }

  public async login(body: LoginReq): Promise<Account | null> {
    try {
      const { jwt } = await firstValueFrom(this.authApi.login({ body }));
      this.jwtCell.value = jwt;
      let account = await firstValueFrom(this.accountsApi.getAccount());

      // FIXME: remove this once backend is updated
      account = AuthService.fixAccountObject(account);

      this.accountCell.value = account;
      return account;
    } catch {
      return null;
    }
  }

  public logout(): void {
    this.jwtCell.value = null;
    this.accountCell.value = null;
  }

  /** @deprecated remove this once backend is updated */
  private static fixAccountObject(brokenAccount: any): Account {
    if (brokenAccount.type && !brokenAccount.role) {
      return brokenAccount; // already fixed
    }

    switch (brokenAccount.role) {
      case "PASSENGER":
        return {
          type: "passenger",
          email: brokenAccount.email,
          fullName: brokenAccount.fullName,
          walletBalanceGrosze: brokenAccount.walletBalancePln * 100,
          phoneNumber: brokenAccount.phoneNumber,
          defaultCreditCardId: brokenAccount.defaultCreditCard,
        };
      case "INSPECTOR":
        return {
          type: "inspector",
          email: brokenAccount.email,
          fullName: brokenAccount.fullName,
        };
      default:
        throw new Error(`Invalid role: ${brokenAccount.role}`);
    }
  }
}
