import { Component } from "@angular/core";
import { ButtonModule } from "primeng/button";

import { TopBarComponent } from "../top-bar/top-bar.component";
import { BackButtonComponent } from "../back-button/back-button.component";

@Component({
  selector: "app-settings",
  standalone: true,
  imports: [ButtonModule, TopBarComponent, BackButtonComponent],
  templateUrl: "./settings.component.html",
})
export class SettingsComponent {}
