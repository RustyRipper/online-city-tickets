import { HttpClientModule } from "@angular/common/http";
import type { Type } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import { ActivatedRoute, RouterModule } from "@angular/router";

import { MemoryStorage } from "./memory-storage";

type Inputs<T> = { [key in keyof T]?: T[key] };
type InputEntries<T> = [keyof T, T[keyof T]][];

type MountOptions<T> = {
  inputs?: Inputs<T>;
  resolvedData?: Record<string, unknown>;
};

type MountResult<T> = Promise<{
  component: T;
}>;

/** Used to mount Angular components for testing. */
export async function mount<T>(
  Component: Type<T>,
  { inputs = {}, resolvedData = {} }: MountOptions<T> = {},
): MountResult<T> {
  const commonImports = [RouterModule.forRoot([]), HttpClientModule];
  const commonProviders = [
    { provide: Storage, useValue: new MemoryStorage() },
    { provide: ActivatedRoute, useValue: { snapshot: { data: resolvedData } } },
  ];
  await TestBed.configureTestingModule({
    imports: [Component, ...commonImports],
    providers: commonProviders,
  }).compileComponents();

  const fixture = TestBed.createComponent(Component);
  const component = fixture.componentInstance;
  for (const [key, value] of Object.entries(inputs) as InputEntries<T>) {
    component[key] = value;
  }
  fixture.detectChanges();
  return { component };
}
