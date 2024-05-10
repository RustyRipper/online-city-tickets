import { Routes } from "@angular/router";

import { hasRole } from "./auth/has-role.guard";

export const routes: Routes = [
  {
    path: "auth",
    canActivate: [hasRole(null)],
    children: [
      {
        path: "login",
        loadComponent: () =>
          import("./auth/login/login.component").then((m) => m.LoginComponent),
      },
      {
        path: "register",
        loadComponent: () =>
          import("./auth/register/register.component").then(
            (m) => m.RegisterComponent,
          ),
      },
    ],
  },
  {
    path: "passenger",
    canActivate: [hasRole("passenger")],
    children: [
      {
        path: "",
        pathMatch: "full",
        redirectTo: "tickets",
      },
      {
        path: "",
        loadComponent: () =>
          import("./passenger/home/home.component").then(
            (m) => m.HomeComponent,
          ),
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
        path: "wallet",
        loadComponent: () =>
          import("./passenger/wallet/wallet.component").then(
            (m) => m.WalletComponent,
          ),
      },
    ],
  },
  {
    path: "inspector",
    canActivate: [hasRole("inspector")],
    children: [
      {
        path: "",
        loadComponent: () =>
          import("./inspector/home/home.component").then(
            (m) => m.HomeComponent,
          ),
      },
    ],
  },
  {
    path: "settings",
    canActivate: [hasRole("any")],
    children: [
      {
        path: "",
        loadComponent: () =>
          import("./shared/settings/settings.component").then(
            (m) => m.SettingsComponent,
          ),
      },
      {
        path: "account",
        loadComponent: () =>
          import("./shared/account-settings/account-settings.component").then(
            (m) => m.AccountSettingsComponent,
          ),
      },
    ],
  },
  {
    path: "**",
    redirectTo: "auth/login",
  },
];
