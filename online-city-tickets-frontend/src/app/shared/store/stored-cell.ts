import { z } from "zod";

import { SCHEMA } from "./schema";

type S = typeof SCHEMA;

export class StoredCell<V> {
  public static of<K extends keyof S & string>(
    storage: Storage,
    key: K,
    defaultValue: z.infer<S[K]>,
  ) {
    const storedValue: z.infer<S[K]> | null = (() => {
      try {
        return SCHEMA[key].parse(JSON.parse(storage.getItem(key) ?? "null"));
      } catch {
        return null;
      }
    })();

    return new StoredCell(storage, key, storedValue ?? defaultValue);
  }

  private constructor(
    private readonly storage: Storage,
    private readonly key: string,
    private v: V,
  ) {}

  get value(): V {
    return this.v;
  }

  set value(value: V) {
    this.v = value;
    this.storage.setItem(this.key, JSON.stringify(value));
  }
}
