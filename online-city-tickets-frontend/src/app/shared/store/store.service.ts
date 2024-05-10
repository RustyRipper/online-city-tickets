import { Injectable } from "@angular/core";
import { z } from "zod";

import { StoreCell } from "./store-cell";
import { Account } from "../../auth/types";

const SCHEMA = {
  ACCOUNT: z
    .intersection(
      z.object({
        fullName: z.string().min(1),
        email: z.string().email(),
      }),
      z.union([
        z.object({
          type: z.literal("passenger"),
          walletBalanceGrosze: z.number().int().nonnegative(),
          phoneNumber: z
            .string()
            .regex(/^[0-9]{9}$/)
            .optional(),
        }),
        z.object({
          type: z.literal("inspector"),
        }),
      ]),
    )
    .nullable(),
  JWT: z
    .string()
    .regex(/^[\w-]*\.[\w-]*\.[\w-]*$/)
    .nullable(),
};

@Injectable({
  providedIn: "root",
})
export class StoreService {
  public readonly jwt: StoreCell<string | null>;
  public readonly account: StoreCell<Account | null>;

  public constructor(storage: Storage) {
    this.jwt = StoreCell.from(storage, SCHEMA, "JWT", null);
    this.account = StoreCell.from(storage, SCHEMA, "ACCOUNT", null);
  }
}
