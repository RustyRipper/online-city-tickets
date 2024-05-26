import { Component, type OnInit } from "@angular/core";
import { RouterOutlet } from "@angular/router";

import { ThemeService } from "~/shared/theme/services/theme.service";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: "./app.component.html",
})
export class AppComponent implements OnInit {
  public constructor(private readonly themeService: ThemeService) {}

  public ngOnInit(): void {
    this.themeService.updateStylesheet();
  }
}
