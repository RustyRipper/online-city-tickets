import { SCHEMA } from "../store/schema";

type S = typeof SCHEMA;
export type Initial = { [K in keyof S]?: string } & Record<string, string>;

/**
 * A in-memory implementation of `Storage` for testing purposes.
 * Each instance of `MemoryStorage` is isolated from others.
 */
export class MemoryStorage implements Storage {
  private readonly map: Map<string, string>;

  public constructor(initial: Initial = {}) {
    this.map = new Map(Object.entries(initial));
  }

  public clear(): void {
    this.map.clear();
  }

  public getItem(key: string): string | null {
    return this.map.get(key) ?? null;
  }

  public key(index: number): string | null {
    return [...this.map.keys()][index];
  }

  public removeItem(key: string): void {
    this.map.delete(key);
  }

  public setItem(key: string, value: string): void {
    this.map.set(key, value);
  }

  public get length(): number {
    return this.map.size;
  }
}
