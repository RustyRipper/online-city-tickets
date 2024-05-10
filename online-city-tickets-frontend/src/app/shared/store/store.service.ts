import { Injectable } from "@angular/core";
import { z } from "zod";

import { StoreCell } from "./store-cell";

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
  public readonly jwt;
  public readonly account;

  public constructor(storage: Storage) {
    this.jwt = new StoreCell(storage, SCHEMA, "JWT", null);
    this.account = new StoreCell(storage, SCHEMA, "ACCOUNT", null);
  }
}
