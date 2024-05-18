import { TestBed } from "@angular/core/testing";
import type { ResolveFn } from "@angular/router";

import { offerResolver } from "./offer.resolver";
import type { TicketOfferDto } from "~/generated/api/models";

describe("offerResolver", () => {
  const executeResolver: ResolveFn<TicketOfferDto> = (...resolverParameters) =>
    TestBed.runInInjectionContext(() => offerResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it("should be created", () => {
    expect(executeResolver).toBeTruthy();
  });
});
