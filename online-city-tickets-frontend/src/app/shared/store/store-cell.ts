import { ZodType, z } from "zod";

export class StoreCell<
  S extends { [key: string]: ZodType<unknown> },
  K extends keyof S,
> {
  private _value: z.infer<S[K]>;
  public constructor(
    private readonly storage: Storage,
    private readonly schema: S,
    private readonly key: K,
    defaultValue: z.infer<S[K]>,
  ) {
    this._value = this._load() ?? defaultValue;
  }

  get value(): z.infer<S[K]> | null {
    return this._value;
  }

  set value(value: z.infer<S[K]>) {
    this._value = value;
    this.storage.setItem(this.key as string, JSON.stringify(value));
  }

  private _load(): z.infer<S[K]> | null {
    try {
      return this.schema[this.key].parse(
        JSON.parse(this.storage.getItem(this.key as string) ?? "null"),
      );
    } catch {
      return null;
    }
  }
}
