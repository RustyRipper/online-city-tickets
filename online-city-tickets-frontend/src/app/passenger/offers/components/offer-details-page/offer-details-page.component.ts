import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import { TicketOfferDto } from "~/generated/api/models";
import { OfferCardComponent } from "~/passenger/offers/components/offer-card/offer-card.component";
import { WalletService } from "~/passenger/wallet/services/wallet.service";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";

type PaymentMethodOption = { label: string; value: string };

@Component({
  selector: "app-offer-details-page",
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    DropdownModule,
    TopBarComponent,
    BackButtonComponent,
    OfferCardComponent,
  ],
  templateUrl: "./offer-details-page.component.html",
  styleUrl: "./offer-details-page.component.css",
})
export class OfferDetailsPageComponent {
  protected readonly paymentMethodOptions: PaymentMethodOption[];
  protected readonly offer: TicketOfferDto;
  protected paymentMethod: string;

  public constructor(activatedRoute: ActivatedRoute) {
    const balance = (activatedRoute.snapshot.data["balance"] / 100).toFixed(2);
    this.offer = activatedRoute.snapshot.data["offer"];
    this.paymentMethodOptions = [
      {
        label: `Wallet (${balance} ${WalletService.currency})`,
        value: "wallet",
      },
      { label: "BLIK", value: "blik" },
      { label: "Credit card", value: "credit-card" },
    ];
    this.paymentMethod = this.paymentMethodOptions[0].value;
  }
}
