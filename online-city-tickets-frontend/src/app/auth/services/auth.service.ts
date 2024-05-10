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
  private readonly _jwt;
  private readonly _account;

  public constructor(
    private readonly accountsApi: AccountsApi,
    private readonly authApi: AuthApi,
    storeService: StoreService,
  ) {
    this._jwt = storeService.jwt;
    this._account = storeService.account;
  }

  public get account(): Account | null {
    return this._account.value;
  }

  public async login(body: LoginReq): Promise<void> {
    const { jwt } = await firstValueFrom(this.authApi.login({ body }));
    this._jwt.value = jwt;
    this._account.value = await firstValueFrom(this.accountsApi.getAccount());
  }
}
