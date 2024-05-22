import { Component } from "@angular/core";

import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";
import { SettingsLinkComponent } from "~/shared/settings/components/settings-link/settings-link.component";

@Component({
  selector: "app-inspector-page",
  standalone: true,
  imports: [TopBarComponent, SettingsLinkComponent],
  templateUrl: "./inspector-page.component.html",
})
export class InspectorPageComponent {
  public constructor(protected readonly i18n: I18nService) {}
}
