import { Injectable } from "@angular/core";
import { z } from "zod";

import { StoreEntry } from "./store-entry";

const SCHEMA = {
  ACCOUNT: z.intersection(
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
  ),
  JWT: z.string().regex(/^[\w-]*\.[\w-]*\.[\w-]*$/),
};

@Injectable({
  providedIn: "root",
})
export class StoreService {
  public readonly jwt;
  public readonly account;

  public constructor(storage: Storage) {
    this.jwt = new StoreEntry(storage, SCHEMA, "JWT");
    this.account = new StoreEntry(storage, SCHEMA, "ACCOUNT");
  }
}
