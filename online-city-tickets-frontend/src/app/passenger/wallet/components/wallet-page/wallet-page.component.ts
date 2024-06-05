import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { PaymentSheetComponent } from "~/passenger/payment/components/payment-sheet/payment-sheet.component";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";

import { WalletService } from "../../services/wallet.service";

@Component({
  selector: "app-wallet-page",
  standalone: true,
  imports: [TopBarComponent, BackButtonComponent, PaymentSheetComponent],
  templateUrl: "./wallet-page.component.html",
  styleUrl: "./wallet-page.component.css",
})
export class WalletPageComponent {
  protected readonly balance;

  public constructor(
    protected readonly i18n: I18nService,
    activatedRoute: ActivatedRoute,
  ) {
    const balanceGrosze = activatedRoute.snapshot.data["balance"];
    this.balance = `${(balanceGrosze / 100).toFixed(2)} ${WalletService.currency}`;
  }
}
