import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { Component } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import { ActivatedRoute, RouterModule } from "@angular/router";

import {
  type Initial as InitialStorage,
  MemoryStorage,
} from "./memory-storage";

@Component({})
class DummyComponent {}

export type ConfigureTestBedOptions = {
  storage?: InitialStorage;
  resolvedData?: Record<string, unknown>;
  customInjects?: () => void;
};

type ConfigureTestBedResult = {
  httpTestingController: HttpTestingController;
};

export function configureTestBed({
  storage,
  resolvedData,
  customInjects,
}: ConfigureTestBedOptions): ConfigureTestBedResult {
  const imports = [
    RouterModule.forRoot([{ path: "**", component: DummyComponent }]),
    HttpClientTestingModule,
  ];

  const providers = [
    { provide: Storage, useValue: new MemoryStorage(storage) },
    {
      provide: ActivatedRoute,
      useValue: { snapshot: { data: resolvedData ?? {} } },
    },
  ];

  TestBed.configureTestingModule({ imports, providers });

  const httpTestingController = TestBed.inject(HttpTestingController);
  customInjects?.();

  return { httpTestingController };
}
