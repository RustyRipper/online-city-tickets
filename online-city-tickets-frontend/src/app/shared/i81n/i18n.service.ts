import { Injectable } from "@angular/core";

import { StoredCell, type StoredCellValue } from "~/shared/store/stored-cell";

import translations from "./translations.json";

type Language = StoredCellValue<"LANGUAGE">;
type TranslationKey = keyof typeof translations;

@Injectable({
  providedIn: "root",
})
export class I18nService {
  private readonly languageCell: StoredCell<Language>;

  public constructor(storage: Storage) {
    this.languageCell = StoredCell.of(storage, "LANGUAGE", "en-US");
  }

  public get language(): Language {
    return this.languageCell.value;
  }

  public set language(language: Language) {
    this.languageCell.value = language;
  }

  public t(
    key: TranslationKey,
    replacements: Record<string, unknown> = {},
  ): string {
    return Object.entries(replacements).reduce(
      (translation, [from, to]) =>
        translation.replaceAll(`{${from}}`, String(to)),
      translations[key][this.language],
    );
  }

  public currency(amountGrosze: number): string {
    return Intl.NumberFormat(this.language, {
      style: "currency",
      currency: "PLN",
      useGrouping: false,
    }).format(amountGrosze / 100);
  }

  public date(date: Date): string {
    return date.toLocaleDateString(this.language);
  }

  public time(date: Date): string {
    return date.toLocaleTimeString(this.language);
  }

  public dateTime(date: Date): string {
    return date.toLocaleString(this.language);
  }
}
