import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { AvatarModule } from "primeng/avatar";

@Component({
  selector: "app-settings-link",
  standalone: true,
  imports: [RouterModule, AvatarModule],
  templateUrl: "./settings-link.component.html",
  styleUrl: "./settings-link.component.css",
})
export class SettingsLinkComponent {}
