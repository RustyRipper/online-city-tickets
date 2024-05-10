import { TestBed } from "@angular/core/testing";

import { StoreService } from "./store.service";

describe("StoreService", () => {
  let service: StoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: Storage,
          useValue: sessionStorage,
        },
      ],
    });
    service = TestBed.inject(StoreService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
