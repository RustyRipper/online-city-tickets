import { Injectable } from "@angular/core";
import { z } from "zod";

import { StoreCell } from "../store-cell";

const SCHEMA = {
  ACCOUNT_TYPE: z.enum(["passenger", "inspector"]).nullable(),
  JWT: z
    .string()
    .regex(/^[\w-]*\.[\w-]*\.[\w-]*$/)
    .nullable(),
  TICKET_KIND: z.enum(["standard", "reduced"]),
  LANGUAGE: z.enum(["en-US", "pl-PL"]),
  THEME: z.enum(["light", "dark"]),
};

@Injectable({
  providedIn: "root",
})
export class StoreService {
  public readonly accountType;
  public readonly jwt;
  public readonly ticketKind;
  public readonly language;
  public readonly theme;

  public constructor(storage: Storage) {
    this.accountType = StoreCell.from(storage, SCHEMA, "ACCOUNT_TYPE", null);
    this.jwt = StoreCell.from(storage, SCHEMA, "JWT", null);
    this.ticketKind = StoreCell.from(
      storage,
      SCHEMA,
      "TICKET_KIND",
      "standard",
    );
    this.language = StoreCell.from(storage, SCHEMA, "LANGUAGE", "en-US");
    this.theme = StoreCell.from(storage, SCHEMA, "THEME", "light");
  }
}
