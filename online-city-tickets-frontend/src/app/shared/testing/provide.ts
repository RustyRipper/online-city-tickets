import type { ProviderToken } from "@angular/core";
import { TestBed } from "@angular/core/testing";

import { type MockHttp, mockHttpFactory } from "./mock-http";
import { type ConfigureTestBedOptions, configureTestBed } from "./test-bed";

type ProvideOptions<I> = ConfigureTestBedOptions<I>;

type ProvideResult<T, I> = {
  sut: T;
  mockHttp: MockHttp;
  injected: I | null;
};

/** Used to provide Angular services for testing. */
export function provide<T, C>(
  token: ProviderToken<T>,
  options: ProvideOptions<C> = {},
): ProvideResult<T, C> {
  const { httpTestingController, injected } = configureTestBed(options);
  const sut = TestBed.inject(token);
  const mockHttp = mockHttpFactory(httpTestingController);
  return { sut, mockHttp, injected };
}
