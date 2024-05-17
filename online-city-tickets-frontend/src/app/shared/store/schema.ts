import { z } from "zod";

export const SCHEMA = {
  ACCOUNT_TYPE: z.enum(["passenger", "inspector"]).nullable(),
  JWT: z
    .string()
    .regex(/^[\w-]*\.[\w-]*\.[\w-]*$/)
    .nullable(),
  TICKET_KIND: z.enum(["standard", "reduced"]),
  LANGUAGE: z.enum(["en-US", "pl-PL"]),
  THEME: z.enum(["light", "dark"]),
};
