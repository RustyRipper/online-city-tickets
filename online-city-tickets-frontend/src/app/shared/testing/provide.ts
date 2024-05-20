import type { ProviderToken } from "@angular/core";
import { TestBed } from "@angular/core/testing";

import { type MockHttp, mockHttpFactory } from "./mock-http";
import { type ConfigureTestBedOptions, configureTestBed } from "./test-bed";

type ProvideOptions = ConfigureTestBedOptions;

type ProvideResult<T> = {
  sut: T;
  mockHttp: MockHttp;
};

/** Used to provide Angular services for testing. */
export function provide<T>(
  token: ProviderToken<T>,
  options: ProvideOptions = {},
): ProvideResult<T> {
  const { httpTestingController } = configureTestBed(options);
  const sut = TestBed.inject(token);
  const mockHttp = mockHttpFactory(httpTestingController);
  return { sut, mockHttp };
}
