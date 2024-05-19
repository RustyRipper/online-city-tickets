import { TestBed } from "@angular/core/testing";
import type { ResolveFn } from "@angular/router";

import { balanceResolver } from "./balance.resolver";

describe(balanceResolver.name, () => {
  const executeResolver: ResolveFn<number> = (...resolverParameters) =>
    TestBed.runInInjectionContext(() => balanceResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it("should be created", () => {
    expect(executeResolver).toBeTruthy();
  });
});
