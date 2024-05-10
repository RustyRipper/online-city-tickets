import { Injectable } from "@angular/core";
import { z } from "zod";

import { StoreEntry } from "./store-entry";

const SCHEMA = {
  IDENTITY: z.object({
    account: z.intersection(
      z.object({
        fullName: z.string().min(1),
        email: z.string().email(),
      }),
      z.union([
        z.object({
          type: z.literal("passenger"),
          walletBalanceGrosze: z.number().int().nonnegative(),
          phoneNumber: z.string().regex(/^[0-9]{9}$/),
        }),
        z.object({
          type: z.literal("inspector"),
        }),
      ]),
    ),
    jwt: z.string().regex(/^[\w-]*\.[\w-]*\.[\w-]*$/),
  }),
};

@Injectable({
  providedIn: "root",
})
export class StoreService {
  public readonly identity;

  public constructor(storage: Storage) {
    this.identity = new StoreEntry(storage, SCHEMA, "IDENTITY");
  }
}
