import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import type { DebugElement, Type } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import { ActivatedRoute, RouterModule } from "@angular/router";

import { API_BASE } from "./consts";
import { type Initial, MemoryStorage } from "./memory-storage";

type Inputs<T> = { [key in keyof T]?: T[key] };
type InputEntries<T> = [keyof T, T[keyof T]][];

type MountOptions<T> = {
  inputs?: Inputs<T>;
  resolvedData?: Record<string, unknown>;
  storage?: Initial;
};

type MountResult<T> = Promise<{
  component: T;
  element: HTMLElement;
  debug: DebugElement;
  mockHttp: (path: string, returned: any) => void;
}>;

/** Used to mount Angular components for testing. */
export async function mount<T>(
  Component: Type<T>,
  { inputs = {}, resolvedData = {}, storage = {} }: MountOptions<T> = {},
): MountResult<T> {
  const commonImports = [RouterModule.forRoot([]), HttpClientTestingModule];
  const commonProviders = [
    { provide: Storage, useValue: new MemoryStorage(storage) },
    { provide: ActivatedRoute, useValue: { snapshot: { data: resolvedData } } },
  ];
  await TestBed.configureTestingModule({
    imports: [Component, ...commonImports],
    providers: commonProviders,
  }).compileComponents();
  const httpTestingController = TestBed.inject(HttpTestingController);

  const fixture = TestBed.createComponent(Component);
  const component = fixture.componentInstance;
  for (const [key, value] of Object.entries(inputs) as InputEntries<T>) {
    component[key] = value;
  }
  fixture.detectChanges();

  const mockHttp = (path: string, returned: any) => {
    httpTestingController.expectOne(API_BASE + path).flush(returned);
    fixture.detectChanges();
  };

  return {
    component,
    element: fixture.nativeElement,
    debug: fixture.debugElement,
    mockHttp,
  };
}
