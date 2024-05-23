import { provide } from "~/shared/testing";

import { I18nService } from "./i18n.service";

describe(I18nService.name, () => {
  it("should be created", () => {
    const { sut } = provide(I18nService);

    expect(sut).toBeTruthy();
  });
});
