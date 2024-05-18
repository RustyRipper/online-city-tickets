import { Injectable } from "@angular/core";
import { StoredCell } from "../../store/stored-cell";

@Injectable({
  providedIn: "root",
})
export class SettingsService {
  public readonly languageCell;
  public readonly themeCell;

  public constructor(storage: Storage) {
    this.languageCell = StoredCell.of(storage, "LANGUAGE", "en-US");
    this.themeCell = StoredCell.of(storage, "THEME", "light");
  }
}
