import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import { TopBarComponent } from "../top-bar/top-bar.component";
import { BackButtonComponent } from "../back-button/back-button.component";

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
    { name: "Standard", value: "standard" },
    { name: "Reduced", value: "reduced" },
  ];
  protected readonly languages = [
    { name: "English", value: "en-US" },
    { name: "Polish", value: "pl-PL" },
  ];
  protected readonly themes = [
    { name: "Light", value: "light" },
    { name: "Dark", value: "dark" },
  ];

  protected selectedTicketKind = this.ticketKinds[0];
  protected selectedLanguage = this.languages[0];
  protected selectedTheme = this.themes[0];
}