import { TestBed } from "@angular/core/testing";
import {
  type MaybeAsync,
  type ResolveFn,
  convertToParamMap,
} from "@angular/router";
import { Observable, firstValueFrom } from "rxjs";

import { type MockHttp, mockHttpFactory } from "./mock-http";
import { type ConfigureTestBedOptions, configureTestBed } from "./test-bed";

type ExecuteOptions = {
  params?: Record<string, string>;
} & ConfigureTestBedOptions<unknown>;

type ExecuteResult<T> = {
  result: Promise<T | null>;
  mockHttp: MockHttp;
};

async function unwrap<T>(maybeAsync: MaybeAsync<T>): Promise<T | null> {
  try {
    if (maybeAsync instanceof Observable) {
      return await firstValueFrom(maybeAsync);
    }
    return await maybeAsync;
  } catch {
    return null;
  }
}

/** Used to execute Angular resolvers and guards for testing. */
export function execute<T>(
  resolver: ResolveFn<T>,
  { params, ...options }: ExecuteOptions = {},
): ExecuteResult<T> {
  const { httpTestingController } = configureTestBed(options);
  const mockHttp = mockHttpFactory(httpTestingController);

  const result = unwrap(
    TestBed.runInInjectionContext(() =>
      resolver({ paramMap: convertToParamMap(params ?? {}) } as any, {} as any),
    ),
  );

  return { result, mockHttp };
}
