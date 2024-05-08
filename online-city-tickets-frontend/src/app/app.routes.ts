import { Routes } from "@angular/router";

export const routes: Routes = [
  {
    path: "",
    redirectTo: "/passenger/shop", // TODO: change to '/login' when login page is ready
    pathMatch: "full",
  },
  {
    path: "passenger",
    redirectTo: "/passenger/shop",
    pathMatch: "full",
  },
  {
    path: "passenger",
    loadComponent: () =>
      import("./passenger/home/home.component").then((m) => m.HomeComponent),
    children: [
      {
        path: "shop",
        loadComponent: () =>
          import("./passenger/shop/shop.component").then(
            (m) => m.ShopComponent,
          ),
      },
      {
        path: "tickets",
        loadComponent: () =>
          import("./passenger/tickets/tickets.component").then(
            (m) => m.TicketsComponent,
          ),
      },
    ],
  },
  {
    path: "passenger/wallet",
    loadComponent: () =>
      import("./passenger/wallet/wallet.component").then(
        (m) => m.WalletComponent,
      ),
  },
  {
    path: "settings",
    loadComponent: () =>
      import("./shared/settings/settings.component").then(
        (m) => m.SettingsComponent,
      ),
  },
];
