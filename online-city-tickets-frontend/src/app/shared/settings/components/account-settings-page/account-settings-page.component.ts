import { Component } from "@angular/core";

import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";

@Component({
  selector: "app-account-settings-page",
  standalone: true,
  imports: [TopBarComponent, BackButtonComponent],
  templateUrl: "./account-settings-page.component.html",
})
export class AccountSettingsPageComponent {}
