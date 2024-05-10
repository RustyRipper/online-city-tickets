import { Injectable } from "@angular/core";

import { StoreService } from "../shared/store.service";
import type { Account, Identity } from "./types";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  public constructor(private readonly storeService: StoreService) {}

  private identity: Identity | null = this.storeService.identity.load();

  public get account(): Account | null {
    return this.identity?.account ?? null;
  }
}
