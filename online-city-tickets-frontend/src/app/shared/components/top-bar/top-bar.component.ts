import { Component, Input } from "@angular/core";
import { ToolbarModule } from "primeng/toolbar";

@Component({
  selector: "app-top-bar",
  standalone: true,
  imports: [ToolbarModule],
  templateUrl: "./top-bar.component.html",
  styleUrl: "./top-bar.component.css",
})
export class TopBarComponent {
  @Input({ required: true }) title!: string;
}
