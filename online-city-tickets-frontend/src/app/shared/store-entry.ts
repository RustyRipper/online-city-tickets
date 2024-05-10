import { ZodType, z } from "zod";

export class StoreEntry<
  S extends { [key: string]: ZodType<unknown> },
  K extends keyof S,
> {
  public constructor(
    private readonly storage: Storage,
    private readonly schema: S,
    private readonly key: K,
  ) {}

  public load(): z.infer<S[K]> | null {
    try {
      return this.schema[this.key].parse(
        JSON.parse(this.storage.getItem(this.key as string) ?? "null"),
      );
    } catch {
      return null;
    }
  }

  public save(value: z.infer<S[K]>) {
    this.storage.setItem(this.key as string, JSON.stringify(value));
  }
}
