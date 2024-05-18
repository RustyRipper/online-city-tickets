import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import { AuthService } from "~/shared/auth/services/auth.service";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
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
  protected readonly languages = [
    { label: "English", value: "en-US" },
    { label: "Polish", value: "pl-PL" },
  ];
  protected readonly themes = [
    { label: "Light", value: "light" },
    { label: "Dark", value: "dark" },
  ];

  public constructor(
    private readonly router: Router,
    private readonly authService: AuthService,
    protected readonly settingsService: SettingsService,
  ) {}

  protected onAccountSwitch(): void {
    this.authService.logout();
    this.router.navigateByUrl("/auth/login");
  }
}
