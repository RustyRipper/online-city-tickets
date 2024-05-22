import { Component } from "@angular/core";

import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";

@Component({
  selector: "app-account-settings-page",
  standalone: true,
  imports: [TopBarComponent, BackButtonComponent],
  templateUrl: "./account-settings-page.component.html",
})
export class AccountSettingsPageComponent {
  public constructor(protected readonly i18n: I18nService) {}
}
