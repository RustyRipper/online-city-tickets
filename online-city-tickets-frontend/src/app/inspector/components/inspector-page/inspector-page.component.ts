import { Component } from "@angular/core";

import { TopBarComponent } from "../../../shared/components/top-bar/top-bar.component";
import { SettingsLinkComponent } from "../../../shared/settings/components/settings-link/settings-link.component";

@Component({
  selector: "app-inspector-page",
  standalone: true,
  imports: [TopBarComponent, SettingsLinkComponent],
  templateUrl: "./inspector-page.component.html",
})
export class InspectorPageComponent {}
