import { ZodType, z } from "zod";

export class StoreCell<V> {
  public static from<
    S extends { [key: string]: ZodType<unknown> },
    K extends keyof S & string,
  >(storage: Storage, schema: S, key: K, defaultValue: z.infer<S[K]>) {
    const storedValue: z.infer<S[K]> | null = (() => {
      try {
        return schema[key].parse(JSON.parse(storage.getItem(key) ?? "null"));
      } catch {
        return null;
      }
    })();

    return new StoreCell(storage, key, storedValue ?? defaultValue);
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
