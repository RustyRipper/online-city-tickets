import { UrlTree } from "@angular/router";

import type { Account } from "~/shared/auth/types";
import { execute } from "~/shared/testing/execute";

import { hasRole } from "./has-role.guard";

const real = (role: Account["type"] | null) => ({
  storage: { ACCOUNT_TYPE: JSON.stringify(role) },
});

describe(hasRole.name, () => {
  for (const role of ["passenger", "inspector", null] as const) {
    it(`should let ${role} in if expected ${role}`, async () => {
      const { result } = execute(hasRole(role), real(role));

      return expect(await result).toBe(true);
    });
  }

  for (const role of ["passenger", "inspector"] as const) {
    it(`should let ${role} in if expected any`, async () => {
      const { result } = execute(hasRole("any"), real(role));

      expect(await result).toBeTrue();
    });

    it(`should redirect ${role} if expected null`, async () => {
      const { result } = execute(hasRole(null), real(role));

      expect(await result).toBeInstanceOf(UrlTree);
    });
  }

  for (const role of ["passenger", "inspector", "any"] as const) {
    it(`should redirect null if expected ${role}`, async () => {
      const { result } = execute(hasRole(role), real(null));

      expect(await result).toBeInstanceOf(UrlTree);
    });
  }
});
