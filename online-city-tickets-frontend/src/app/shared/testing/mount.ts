import type { DebugElement, Type } from "@angular/core";
import { TestBed } from "@angular/core/testing";

import { type MockHttp, mockHttpFactory } from "./mock-http";
import { type ConfigureTestBedOptions, configureTestBed } from "./test-bed";

type Inputs<T> = { [key in keyof T]?: T[key] };
type InputEntries<T> = [keyof T, T[keyof T]][];

type MountOptions<T> = {
  inputs?: Inputs<T>;
} & ConfigureTestBedOptions;

type MountResult<T> = Promise<{
  component: T;
  element: HTMLElement;
  debug: DebugElement;
  mockHttp: MockHttp;
}>;

/** Used to mount Angular components for testing. */
export async function mount<T>(
  Component: Type<T>,
  { inputs, resolvedData, storage }: MountOptions<T> = {},
): MountResult<T> {
  const { httpTestingController } = configureTestBed({ storage, resolvedData });
  await TestBed.compileComponents();

  const fixture = TestBed.createComponent(Component);
  const component = fixture.componentInstance;
  for (const [key, value] of Object.entries(inputs ?? {}) as InputEntries<T>) {
    component[key] = value;
  }
  fixture.detectChanges();

  return {
    component,
    element: fixture.nativeElement,
    debug: fixture.debugElement,
    mockHttp: mockHttpFactory(httpTestingController, () =>
      fixture.detectChanges(),
    ),
  };
}
