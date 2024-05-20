import { MemoryStorage } from "~/shared/testing/memory-storage";

import { StoredCell } from "./stored-cell";

describe(StoredCell.name, () => {
  it("should use default value if storage is empty", () => {
    const storage = new MemoryStorage();

    const cell = StoredCell.of(storage, "LANGUAGE", "en-US");

    expect(cell.value).toBe("en-US");
  });

  it("should use stored value if storage is not empty", () => {
    const storage = new MemoryStorage({ LANGUAGE: JSON.stringify("pl-PL") });

    const cell = StoredCell.of(storage, "LANGUAGE", "en-US");

    expect(cell.value).toBe("pl-PL");
  });

  it("should use default value if stored value is invalid", () => {
    const storage = new MemoryStorage({ LANGUAGE: "invalid" });

    const cell = StoredCell.of(storage, "LANGUAGE", "en-US");

    expect(cell.value).toBe("en-US");
  });

  it("should save new values to the storage", () => {
    const storage = new MemoryStorage();
    const cell = StoredCell.of(storage, "LANGUAGE", "en-US");

    cell.value = "pl-PL";

    expect(storage.getItem("LANGUAGE")).toBe(JSON.stringify("pl-PL"));
  });
});
