import { TestBed } from "@angular/core/testing";
import { HttpClientModule } from "@angular/common/http";

import { AuthService } from "./auth.service";

describe("AuthService", () => {
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
