import { Component } from "@angular/core";
import { MenuItem } from "primeng/api";
import { TabMenuModule } from "primeng/tabmenu";

import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { WalletIndicatorComponent } from "~/passenger/wallet/components/wallet-indicator/wallet-indicator.component";
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
  protected readonly tabs: MenuItem[] = [
    {
      label: "Tickets",
      icon: "pi pi-ticket",
      routerLink: "tickets",
    },
    {
      label: "Shop",
      icon: "pi pi-shopping-cart",
      routerLink: "offers",
    },
  ];
}
