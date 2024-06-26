import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import { AuthService } from "~/shared/auth/services/auth.service";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i18n/i18n.service";
import { ThemeService } from "~/shared/theme/services/theme.service";

@Component({
  selector: "app-settings-page",
  standalone: true,
  imports: [
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
    protected readonly themeService: ThemeService,
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
