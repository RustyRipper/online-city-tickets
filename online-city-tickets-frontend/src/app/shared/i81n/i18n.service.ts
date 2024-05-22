import { Injectable } from "@angular/core";
import type { z } from "zod";

import { SettingsService } from "~/shared/settings/services/settings.service";
import type { SCHEMA } from "~/shared/store/schema";

import translations from "./translations.json";

type Language = z.infer<(typeof SCHEMA)["LANGUAGE"]>;
type TranslationKey = keyof typeof translations;

@Injectable({
  providedIn: "root",
})
export class I18nService {
  public constructor(private readonly settingsService: SettingsService) {}

  public get language(): Language {
    return this.settingsService.languageCell.value;
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
}
