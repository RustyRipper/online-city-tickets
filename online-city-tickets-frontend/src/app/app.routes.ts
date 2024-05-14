import { Routes } from "@angular/router";

import { hasRole } from "./auth/guards/has-role.guard";

export const routes: Routes = [
  {
    path: "auth",
    canActivate: [hasRole(null)],
    children: [
      {
        path: "login",
        loadComponent: () =>
          import("./auth/components/login/login.component").then(
            (m) => m.LoginComponent,
          ),
      },
      {
        path: "register",
        loadComponent: () =>
          import("./auth/components/register/register.component").then(
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
          import("./passenger/components/home/home.component").then(
            (m) => m.HomeComponent,
          ),
        children: [
          {
            path: "tickets",
            loadComponent: () =>
              import("./passenger/components/tickets/tickets.component").then(
                (m) => m.TicketsComponent,
              ),
          },
          {
            path: "shop",
            loadComponent: () =>
              import(
                "./passenger/offers/components/offer-list/offer-list.component"
              ).then((m) => m.OfferListComponent),
          },
        ],
      },
      {
        path: "wallet",
        loadComponent: () =>
          import("./passenger/wallet/components/wallet/wallet.component").then(
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
          import("./inspector/components/home/home.component").then(
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
          import(
            "./shared/settings/components/settings/settings.component"
          ).then((m) => m.SettingsComponent),
      },
      {
        path: "account",
        loadComponent: () =>
          import(
            "./shared/settings/components/account-settings/account-settings.component"
          ).then((m) => m.AccountSettingsComponent),
      },
    ],
  },
  {
    path: "**",
    redirectTo: "auth/login",
  },
];
