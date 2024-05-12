import { Component } from "@angular/core";
import { MenuItem } from "primeng/api";
import { TabMenuModule } from "primeng/tabmenu";
import { AvatarModule } from "primeng/avatar";

import { TopBarComponent } from "../../../shared/components/top-bar/top-bar.component";
import { WalletIndicatorComponent } from "../../wallet/components/wallet-indicator/wallet-indicator.component";

@Component({
  selector: "app-home",
  standalone: true,
  imports: [
    TabMenuModule,
    AvatarModule,
    TopBarComponent,
    WalletIndicatorComponent,
  ],
  templateUrl: "./home.component.html",
  styleUrl: "./home.component.css",
})
export class HomeComponent {
  protected readonly tabs: MenuItem[] = [
    {
      label: "Tickets",
      icon: "pi pi-ticket",
      routerLink: "tickets",
    },
    {
      label: "Shop",
      icon: "pi pi-shopping-cart",
      routerLink: "shop",
    },
  ];
}
