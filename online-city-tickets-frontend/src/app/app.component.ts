import { Component, type OnInit } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { ToastModule } from "primeng/toast";

import { ThemeService } from "~/shared/theme/services/theme.service";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [RouterOutlet, ToastModule],
  templateUrl: "./app.component.html",
})
export class AppComponent implements OnInit {
  public constructor(private readonly themeService: ThemeService) {}

  public ngOnInit(): void {
    this.themeService.updateStylesheet();
  }
}
