import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

import type { LoginReq, RegisterAsPassengerReq } from "~/generated/api/models";
import { AccountsApi, AuthApi } from "~/generated/api/services";
import type { Account } from "~/shared/auth/types";
import { StoredCell } from "~/shared/store/stored-cell";

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

  public async login(body: LoginReq): Promise<Account> {
    this.jwtCell.value = null;
    const { jwt } = await firstValueFrom(this.authApi.login({ body }));
    this.jwtCell.value = jwt;
    let account = await firstValueFrom(this.accountsApi.getAccount());
    this.accountTypeCell.value = account.type;
    return account;
  }

  public logout(): void {
    this.jwtCell.value = null;
    this.accountTypeCell.value = null;
  }

  public async register(body: RegisterAsPassengerReq): Promise<Account> {
    this.jwtCell.value = null;
    await firstValueFrom(this.authApi.registerAsPassenger({ body }));
    return this.login({ email: body.email, password: body.password });
  }
}
