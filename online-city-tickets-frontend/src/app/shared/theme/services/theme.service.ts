import { DOCUMENT } from "@angular/common";
import { Inject, Injectable } from "@angular/core";

import { StoredCell, type StoredCellValue } from "~/shared/store/stored-cell";

type Theme = StoredCellValue<"THEME">;

const STYLESHEETS = {
  light: "aura-light-blue.css",
  dark: "aura-dark-blue.css",
} as const satisfies Record<Theme, string>;

@Injectable({
  providedIn: "root",
})
export class ThemeService {
  private readonly themeCell: StoredCell<Theme>;
  private readonly themeLink: HTMLLinkElement;
  private readonly body: HTMLBodyElement;

  public constructor(storage: Storage, @Inject(DOCUMENT) document: Document) {
    this.themeCell = StoredCell.of(storage, "THEME", "light");
    this.themeLink = document.getElementById("theme-link") as HTMLLinkElement;
    this.body = document.body as HTMLBodyElement;
  }

  public get theme(): Theme {
    return this.themeCell.value;
  }

  public set theme(theme: Theme) {
    if (this.theme !== theme) {
      this.themeCell.value = theme;
      this.updateStylesheet();
    }
  }

  public updateStylesheet() {
    this.themeLink.href = STYLESHEETS[this.theme];
    this.body.classList.remove(...Object.keys(STYLESHEETS));
    this.body.classList.add(this.theme);
  }
}
