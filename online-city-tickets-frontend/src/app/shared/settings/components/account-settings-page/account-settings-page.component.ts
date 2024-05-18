import { Component } from "@angular/core";

import { TopBarComponent } from "../../../components/top-bar/top-bar.component";
import { BackButtonComponent } from "../../../components/back-button/back-button.component";

@Component({
  selector: "app-account-settings-page",
  standalone: true,
  imports: [TopBarComponent, BackButtonComponent],
  templateUrl: "./account-settings-page.component.html",
})
export class AccountSettingsPageComponent {}
