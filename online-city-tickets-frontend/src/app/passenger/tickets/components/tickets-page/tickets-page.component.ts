import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";

@Component({
  selector: "app-tickets-page",
  standalone: true,
  imports: [RouterModule, ButtonModule],
  templateUrl: "./tickets-page.component.html",
  styleUrl: "./tickets-page.component.css",
})
export class TicketsPageComponent {}
