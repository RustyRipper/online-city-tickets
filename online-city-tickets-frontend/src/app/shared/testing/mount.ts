import type { DebugElement, Type } from "@angular/core";
import { TestBed } from "@angular/core/testing";

import { type MockHttp, mockHttpFactory } from "./mock-http";
import { type ConfigureTestBedOptions, configureTestBed } from "./test-bed";

type Inputs<T> = { [key in keyof T]?: T[key] };
type InputEntries<T> = [keyof T, T[keyof T]][];

type MountOptions<T> = {
  inputs?: Inputs<T>;
} & ConfigureTestBedOptions<unknown>;

type MountResult<T> = Promise<{
  sut: T;
  element: HTMLElement;
  debug: DebugElement;
  mockHttp: MockHttp;
}>;

/** Used to mount Angular components for testing. */
export async function mount<T>(
  Component: Type<T>,
  { inputs, ...options }: MountOptions<T> = {},
): MountResult<T> {
  const { httpTestingController } = configureTestBed(options);
  await TestBed.compileComponents();

  const fixture = TestBed.createComponent(Component);
  const sut = fixture.componentInstance;
  for (const [key, value] of Object.entries(inputs ?? {}) as InputEntries<T>) {
    sut[key] = value;
  }
  fixture.detectChanges();

  const mockHttp = mockHttpFactory(httpTestingController, () =>
    fixture.detectChanges(),
  );

  return {
    sut,
    element: fixture.nativeElement,
    debug: fixture.debugElement,
    mockHttp,
  };
}
