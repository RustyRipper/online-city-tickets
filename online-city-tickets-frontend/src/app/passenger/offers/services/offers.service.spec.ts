import { HttpClientModule } from "@angular/common/http";
import { TestBed } from "@angular/core/testing";

import { OffersService } from "./offers.service";

describe(OffersService.name, () => {
  let service: OffersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [{ provide: Storage, useValue: sessionStorage }],
    });
    service = TestBed.inject(OffersService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
