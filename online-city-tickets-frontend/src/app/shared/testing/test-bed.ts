import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { Component } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { ActivatedRoute, RouterModule } from "@angular/router";
import { MessageService } from "primeng/api";

import {
  type Initial as InitialStorage,
  MemoryStorage,
} from "./memory-storage";

@Component({})
class DummyComponent {}

export type ConfigureTestBedOptions<I> = {
  storage?: InitialStorage;
  resolvedData?: Record<string, unknown>;
  injects?: (testBed: TestBed) => I;
};

type ConfigureTestBedResult<I> = {
  httpTestingController: HttpTestingController;
  injected: I | null;
};

export function configureTestBed<I>({
  storage,
  resolvedData,
  injects,
}: ConfigureTestBedOptions<I>): ConfigureTestBedResult<I> {
  const imports = [
    RouterModule.forRoot([{ path: "**", component: DummyComponent }]),
    NoopAnimationsModule,
    HttpClientTestingModule,
  ];

  const providers = [
    { provide: MessageService, useClass: MessageService },
    { provide: Storage, useValue: new MemoryStorage(storage) },
    {
      provide: ActivatedRoute,
      useValue: { snapshot: { data: resolvedData ?? {} } },
    },
  ];

  TestBed.configureTestingModule({ imports, providers });

  const httpTestingController = TestBed.inject(HttpTestingController);
  const injected = injects?.(TestBed) ?? null;

  return { httpTestingController, injected };
}
