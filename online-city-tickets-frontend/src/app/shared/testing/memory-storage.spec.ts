import { MemoryStorage } from "./memory-storage";

describe(MemoryStorage.name, () => {
  it("should be constructable without arguments", () => {
    const sut = new MemoryStorage();

    expect(sut.length).toBe(0);
  });

  it("should be constructable with an initial state", () => {
    const sut = new MemoryStorage({ first: "value", second: "other" });

    expect(sut.length).toBe(2);
    expect(sut.getItem("first")).toBe("value");
    expect(sut.getItem("second")).toBe("other");
  });

  it("should be isolated between instances", () => {
    const first = new MemoryStorage({ key: "value" });
    const second = new MemoryStorage();

    expect(first.length).toBe(1);
    expect(first.getItem("key")).toBe("value");
    expect(second.length).toBe(0);
    expect(second.getItem("key")).toBeNull();
  });

  it("should be clearable", () => {
    const sut = new MemoryStorage({ key: "value" });

    sut.clear();

    expect(sut.length).toBe(0);
    expect(sut.getItem("key")).toBeNull();
  });

  it("should be ordered", () => {
    const sut = new MemoryStorage();

    sut.setItem("first", "value");
    sut.setItem("second", "other");

    expect(sut.key(0)).toBe("first");
    expect(sut.key(1)).toBe("second");
  });

  it("can remove items", () => {
    const sut = new MemoryStorage({ key: "value" });

    sut.removeItem("key");

    expect(sut.length).toBe(0);
    expect(sut.getItem("key")).toBeNull();
  });
});
