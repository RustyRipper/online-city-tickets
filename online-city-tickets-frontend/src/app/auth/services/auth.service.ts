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

  public async login(body: LoginReq): Promise<void> {
    const { jwt } = await firstValueFrom(this.authApi.login({ body }));
    this.jwtCell.value = jwt;
    const account = await firstValueFrom(this.accountsApi.getAccount());
    this.accountCell.value = account;
  }
}
