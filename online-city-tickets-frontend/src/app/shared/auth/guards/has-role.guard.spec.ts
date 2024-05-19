import { TestBed } from "@angular/core/testing";
import type { CanActivateFn } from "@angular/router";

import { hasRole } from "./has-role.guard";

describe(hasRole.name, () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
    TestBed.runInInjectionContext(() => hasRole(null)(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it("should be created", () => {
    expect(executeGuard).toBeTruthy();
  });
});
