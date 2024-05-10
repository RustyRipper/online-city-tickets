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
  public constructor(
    private readonly storeService: StoreService,
    private readonly accountsApi: AccountsApi,
    private readonly authApi: AuthApi,
  ) {}

  private _jwt: string | null = this.storeService.jwt.load();
  private _account: Account | null = this.storeService.account.load();

  public get jwt(): string | null {
    return this._jwt;
  }

  public get account(): Account | null {
    return this._account;
  }

  public async login(body: LoginReq): Promise<void> {
    const { jwt } = await firstValueFrom(this.authApi.login({ body }));
    this._jwt = jwt;
    this.storeService.jwt.save(jwt);
    this._account = await firstValueFrom(this.accountsApi.getAccount());
    this.storeService.account.save(this._account);
  }
}
