import type { HttpTestingController } from "@angular/common/http/testing";

const API_BASE = "http://localhost:8080/api/v1";

export type MockHttp = (path: string, returned: any, status?: number) => void;

export function mockHttpFactory(
  httpTestingController: HttpTestingController,
  afterFlush?: () => void,
): MockHttp {
  return (path, returned, status = 200) => {
    httpTestingController
      .expectOne(API_BASE + path)
      .flush(returned, { status, statusText: String(status) });
    afterFlush?.();
  };
}
