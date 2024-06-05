import { Component } from "@angular/core";

import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";

@Component({
  selector: "app-card-list-page",
  standalone: true,
  imports: [TopBarComponent, BackButtonComponent],
  templateUrl: "./card-list-page.component.html",
  styleUrl: "./card-list-page.component.css",
})
export class CardListPageComponent {}
