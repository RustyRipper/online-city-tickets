import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import { AuthService } from "~/shared/auth/services/auth.service";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";
import { SettingsService } from "~/shared/settings/services/settings.service";

@Component({
  selector: "app-settings-page",
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ButtonModule,
    DropdownModule,
    TopBarComponent,
    BackButtonComponent,
  ],
  templateUrl: "./settings-page.component.html",
  styleUrl: "./settings-page.component.css",
})
export class SettingsPageComponent {
  public constructor(
    private readonly router: Router,
    private readonly authService: AuthService,
    protected readonly settingsService: SettingsService,
    protected readonly i18n: I18nService,
  ) {}

  protected get languages() {
    return [
      { label: this.i18n.t("settings-page.english"), value: "en-US" },
      { label: this.i18n.t("settings-page.polish"), value: "pl-PL" },
    ];
  }

  protected get themes() {
    return [
      { label: this.i18n.t("settings-page.light"), value: "light" },
      { label: this.i18n.t("settings-page.dark"), value: "dark" },
    ];
  }

  protected onAccountSwitch(): void {
    this.authService.logout();
    this.router.navigateByUrl("/auth/login");
  }
}
