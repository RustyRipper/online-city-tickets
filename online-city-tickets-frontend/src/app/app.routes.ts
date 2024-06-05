import type { Routes } from "@angular/router";

import { offerResolver } from "~/passenger/offers/resolvers/offer.resolver";
import { ticketResolver } from "~/passenger/tickets/resolvers/ticket.resolver";
import { balanceResolver } from "~/passenger/wallet/resolvers/balance.resolver";
import { hasRole } from "~/shared/auth/guards/has-role.guard";
import { accountResolver } from "~/shared/auth/resolvers/account.resolver";

export const routes: Routes = [
  {
    path: "auth",
    canActivate: [hasRole(null)],
    children: [
      {
        path: "login",
        loadComponent: () =>
          import(
            "~/shared/auth/components/login-page/login-page.component"
          ).then((m) => m.LoginPageComponent),
      },
      {
        path: "register",
        loadComponent: () =>
          import(
            "~/shared/auth/components/register-page/register-page.component"
          ).then((m) => m.RegisterPageComponent),
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
          import("~/passenger/components/home-page/home-page.component").then(
            (m) => m.HomePageComponent,
          ),
        children: [
          {
            path: "tickets",
            loadComponent: () =>
              import(
                "~/passenger/tickets/components/ticket-list-page/ticket-list-page.component"
              ).then((m) => m.TicketListPageComponent),
          },
          {
            path: "offers",
            loadComponent: () =>
              import(
                "~/passenger/offers/components/offer-list-page/offer-list-page.component"
              ).then((m) => m.OfferListPageComponent),
          },
        ],
      },
      {
        path: "offers/:id",
        loadComponent: () =>
          import(
            "~/passenger/offers/components/offer-details-page/offer-details-page.component"
          ).then((m) => m.OfferDetailsPageComponent),
        resolve: { offer: offerResolver },
      },
      {
        path: "tickets/:code",
        loadComponent: () =>
          import(
            "~/passenger/tickets/components/ticket-details-page/ticket-details-page.component"
          ).then((m) => m.TicketDetailsPageComponent),
        resolve: { ticket: ticketResolver, account: accountResolver },
      },
      {
        path: "wallet",
        loadComponent: () =>
          import(
            "~/passenger/wallet/components/wallet-page/wallet-page.component"
          ).then((m) => m.WalletPageComponent),
        resolve: { balance: balanceResolver },
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
          import(
            "~/inspector/components/inspector-page/inspector-page.component"
          ).then((m) => m.InspectorPageComponent),
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
            "~/shared/settings/components/settings-page/settings-page.component"
          ).then((m) => m.SettingsPageComponent),
      },
      {
        path: "account",
        loadComponent: () =>
          import(
            "~/shared/settings/components/account-settings-page/account-settings-page.component"
          ).then((m) => m.AccountSettingsPageComponent),
        resolve: { account: accountResolver },
      },
    ],
  },
  {
    path: "**",
    redirectTo: "auth/login",
  },
];
