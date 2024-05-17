import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

import type { Account } from "../types";
import type {
  LoginReq,
  RegisterAsPassengerReq,
} from "../../../generated/api/models";
import { AccountsApi, AuthApi } from "../../../generated/api/services";
import { StoredCell } from "../../store/stored-cell";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private readonly jwtCell;
  private readonly accountTypeCell;

  public constructor(
    private readonly accountsApi: AccountsApi,
    private readonly authApi: AuthApi,
    storage: Storage,
  ) {
    this.jwtCell = StoredCell.of(storage, "JWT", null);
    this.accountTypeCell = StoredCell.of(storage, "ACCOUNT_TYPE", null);
  }

  public get jwt(): string | null {
    return this.jwtCell.value;
  }

  public get accountType(): Account["type"] | null {
    return this.accountTypeCell.value;
  }

  public async login(body: LoginReq): Promise<Account | null> {
    try {
      this.jwtCell.value = null;
      const { jwt } = await firstValueFrom(this.authApi.login({ body }));
      this.jwtCell.value = jwt;
      let account = await firstValueFrom(this.accountsApi.getAccount());

      // FIXME: remove this once backend is updated
      account = AuthService.fixAccountObject(account);

      this.accountTypeCell.value = account.type;
      return account;
    } catch {
      return null;
    }
  }

  public logout(): void {
    this.jwtCell.value = null;
    this.accountTypeCell.value = null;
  }

  public async register(body: RegisterAsPassengerReq): Promise<Account | null> {
    try {
      this.jwtCell.value = null;
      const { ok } = await firstValueFrom(
        this.authApi.registerAsPassenger$Response({ body }),
      );
      if (!ok) {
        return null;
      }
      return this.login({ email: body.email, password: body.password });
    } catch {
      return null;
    }
  }

  /** @deprecated remove this once backend is updated */
  public static fixAccountObject(brokenAccount: any): Account {
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
