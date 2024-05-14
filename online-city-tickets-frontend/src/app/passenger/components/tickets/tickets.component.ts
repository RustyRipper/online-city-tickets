import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";

@Component({
  selector: "app-tickets",
  standalone: true,
  imports: [RouterModule, ButtonModule],
  templateUrl: "./tickets.component.html",
  styleUrl: "./tickets.component.css",
})
export class TicketsComponent {}
