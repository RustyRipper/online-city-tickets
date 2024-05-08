import { Location } from "@angular/common";
import { Component } from "@angular/core";
import { ButtonModule } from "primeng/button";

@Component({
  selector: "app-back-button",
  standalone: true,
  imports: [ButtonModule],
  templateUrl: "./back-button.component.html",
  styleUrl: "./back-button.component.css",
})
export class BackButtonComponent {
  public constructor(private readonly location: Location) {}

  protected onClick() {
    this.location.back();
  }
}
