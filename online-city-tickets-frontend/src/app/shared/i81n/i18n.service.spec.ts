import { provide } from "~/shared/testing";

import { I18nService } from "./i18n.service";

describe(I18nService.name, () => {
  it("should read preferred language from storage", () => {
    const { sut } = provide(I18nService, {
      storage: {
        LANGUAGE: JSON.stringify("pl-PL"),
      },
    });

    expect(sut.language).toBe("pl-PL");
  });

  it("should provide translations", () => {
    const { sut } = provide(I18nService);

    sut.language = "en-US";
    expect(sut.t("settings-page.english")).toBe("English");

    sut.language = "pl-PL";
    expect(sut.t("settings-page.english")).toBe("Angielski");
  });
});
