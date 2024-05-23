import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";

import { I18nService } from "~/shared/i81n/i18n.service";

@Component({
  selector: "app-tickets-page",
  standalone: true,
  imports: [RouterModule, ButtonModule],
  templateUrl: "./tickets-page.component.html",
  styleUrl: "./tickets-page.component.css",
})
export class TicketsPageComponent {
  public constructor(protected readonly i18n: I18nService) {}
}
