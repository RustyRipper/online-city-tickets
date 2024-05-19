import { HttpClientModule } from "@angular/common/http";
import { TestBed } from "@angular/core/testing";

import { AuthService } from "./auth.service";

describe(AuthService.name, () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        {
          provide: Storage,
          useValue: sessionStorage,
        },
      ],
    });
    service = TestBed.inject(AuthService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
