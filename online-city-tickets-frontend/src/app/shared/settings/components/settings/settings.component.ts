import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import { TopBarComponent } from "../../../components/top-bar/top-bar.component";
import { BackButtonComponent } from "../../../components/back-button/back-button.component";
import { AuthService } from "../../../../auth/services/auth.service";
import { StoreService } from "../../../store/store.service";

@Component({
  selector: "app-settings",
  standalone: true,
  imports: [
    FormsModule,
    RouterModule,
    ButtonModule,
    DropdownModule,
    TopBarComponent,
    BackButtonComponent,
  ],
  templateUrl: "./settings.component.html",
  styleUrl: "./settings.component.css",
})
export class SettingsComponent {
  protected readonly ticketKinds = [
    { label: "Standard", value: "standard" },
    { label: "Reduced", value: "reduced" },
  ];
  protected readonly languages = [
    { label: "English", value: "en-US" },
    { label: "Polish", value: "pl-PL" },
  ];
  protected readonly themes = [
    { label: "Light", value: "light" },
    { label: "Dark", value: "dark" },
  ];

  protected readonly ticketKindCell;
  protected readonly languageCell;
  protected readonly themeCell;

  public constructor(
    private readonly router: Router,
    private readonly authService: AuthService,
    storeService: StoreService,
  ) {
    this.ticketKindCell = storeService.ticketKind;
    this.languageCell = storeService.language;
    this.themeCell = storeService.theme;
  }

  protected onAccountSwitch(): void {
    this.authService.logout();
    this.router.navigate(["/auth/login"]);
  }
}
