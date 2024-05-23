import { Component } from "@angular/core";
import type { MenuItem } from "primeng/api";
import { TabMenuModule } from "primeng/tabmenu";

import { WalletIndicatorComponent } from "~/passenger/wallet/components/wallet-indicator/wallet-indicator.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";
import { SettingsLinkComponent } from "~/shared/settings/components/settings-link/settings-link.component";

@Component({
  selector: "app-home-page",
  standalone: true,
  imports: [
    TabMenuModule,
    TopBarComponent,
    WalletIndicatorComponent,
    SettingsLinkComponent,
  ],
  templateUrl: "./home-page.component.html",
  styleUrl: "./home-page.component.css",
})
export class HomePageComponent {
  public constructor(protected readonly i18n: I18nService) {}

  protected readonly tabs: MenuItem[] = [
    {
      label: this.i18n.t("home-page.tickets"),
      icon: "pi pi-ticket",
      routerLink: "tickets",
    },
    {
      label: this.i18n.t("home-page.shop"),
      icon: "pi pi-shopping-cart",
      routerLink: "offers",
    },
  ];
}
