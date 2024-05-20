import { provide } from "~/shared/testing";

import { SettingsService } from "./settings.service";

describe(SettingsService.name, () => {
  it("should read preferences from storage", () => {
    const { sut } = provide(SettingsService, {
      storage: {
        LANGUAGE: JSON.stringify("pl-PL"),
        THEME: JSON.stringify("dark"),
      },
    });

    expect(sut.languageCell.value).toBe("pl-PL");
    expect(sut.themeCell.value).toBe("dark");
  });
});
