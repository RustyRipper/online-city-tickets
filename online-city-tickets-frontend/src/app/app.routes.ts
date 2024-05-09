import { Routes } from "@angular/router";

export const routes: Routes = [
  {
    path: "",
    redirectTo: "/passenger/tickets", // TODO: change to '/login' when login page is ready
    pathMatch: "full",
  },
  {
    path: "passenger",
    redirectTo: "/passenger/tickets",
    pathMatch: "full",
  },
  {
    path: "passenger",
    loadComponent: () =>
      import("./passenger/home/home.component").then((m) => m.HomeComponent),
    children: [
      {
        path: "tickets",
        loadComponent: () =>
          import("./passenger/tickets/tickets.component").then(
            (m) => m.TicketsComponent,
          ),
      },
      {
        path: "shop",
        loadComponent: () =>
          import("./passenger/shop/shop.component").then(
            (m) => m.ShopComponent,
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
  {
    path: "settings/account",
    loadComponent: () =>
      import("./shared/account-settings/account-settings.component").then(
        (m) => m.AccountSettingsComponent,
      ),
  },
];
