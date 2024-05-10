import { Injectable } from "@angular/core";

type Passenger = {
  type: "passenger";
  fullName: string;
  email: string;
  walletBalanceGrosze: number;
  phoneNumber: string;
};

type Inspector = { type: "inspector"; fullName: string; email: string };

export type Account = Passenger | Inspector;

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private _account: Account | null = null;

  public get account(): Account | null {
    return this._account;
  }
}
