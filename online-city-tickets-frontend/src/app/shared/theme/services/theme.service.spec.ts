import { DOCUMENT } from "@angular/common";
import type { TestBed } from "@angular/core/testing";

import { provide } from "~/shared/testing";

import { ThemeService } from "./theme.service";

export function setupDocument(tb: TestBed) {
  const document = tb.inject(DOCUMENT);

  const themeLink = document.createElement("link");
  themeLink.id = "theme-link";
  themeLink.rel = "stylesheet";
  document.head.appendChild(themeLink);

  return document;
}

describe(ThemeService.name, () => {
  it("should read preferred theme from storage", () => {
    const { sut } = provide(ThemeService, {
      storage: {
        THEME: JSON.stringify("dark"),
      },
      injects: setupDocument,
    });

    expect(sut.theme).toBe("dark");
  });

  it("should update stylesheet's href", () => {
    const { sut, injected } = provide(ThemeService, {
      injects: setupDocument,
    });

    sut.theme = "dark";

    const themeLink = injected!.getElementById("theme-link") as HTMLLinkElement;
    expect(themeLink.href).toContain("dark");
  });

  it("should update body's class", () => {
    const { sut, injected } = provide(ThemeService, {
      injects: setupDocument,
    });

    sut.theme = "dark";

    expect(injected!.body.classList).toContain("dark");
  });
});
