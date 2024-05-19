import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { TestBed } from "@angular/core/testing";
import {
  type MaybeAsync,
  type ResolveFn,
  RouterModule,
  convertToParamMap,
} from "@angular/router";
import { Observable, firstValueFrom } from "rxjs";

import { API_BASE } from "./consts";
import { type Initial, MemoryStorage } from "./memory-storage";

type ExecuteOptions = {
  params?: Record<string, string>;
  storage?: Initial;
  customInjects?: () => void;
};

type ExecuteResult<T> = {
  result: Promise<T | null>;
  mockHttp: (path: string, returned: any, status?: number) => void;
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
  { params = {}, storage = {}, customInjects }: ExecuteOptions = {},
): ExecuteResult<T> {
  const imports = [
    RouterModule.forRoot([{ path: "**", component: {} as any }]),
    HttpClientTestingModule,
  ];
  const providers = [
    { provide: Storage, useValue: new MemoryStorage(storage) },
  ];
  TestBed.configureTestingModule({ imports, providers });
  const httpTestingController = TestBed.inject(HttpTestingController);
  customInjects?.();

  const result = unwrap(
    TestBed.runInInjectionContext(() =>
      resolver({ paramMap: convertToParamMap(params) } as any, {} as any),
    ),
  );

  const mockHttp = (path: string, returned: any, status: number = 200) =>
    httpTestingController
      .expectOne(API_BASE + path)
      .flush(returned, { status, statusText: String(status) });

  return { result, mockHttp };
}
