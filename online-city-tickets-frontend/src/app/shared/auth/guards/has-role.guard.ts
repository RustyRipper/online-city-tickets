import { inject } from "@angular/core";
import { type CanActivateFn, Router } from "@angular/router";

import type { Account } from "~/shared/auth/types";
import { AuthService } from "~/shared/auth/services/auth.service";

type RoleName = Account["type"] | "any" | null;

export const hasRole: (expected: RoleName) => CanActivateFn =
  (expected) => (_route, _state) => {
    const router = inject(Router);
    const service = inject(AuthService);

    const real = service.accountType;

    if (real === expected || (real !== null && expected === "any")) {
      return true;
    }

    switch (real) {
      case "passenger":
        return router.parseUrl("/passenger");
      case "inspector":
        return router.parseUrl("/inspector");
      case null:
        return router.parseUrl("/auth/login");
    }
  };
