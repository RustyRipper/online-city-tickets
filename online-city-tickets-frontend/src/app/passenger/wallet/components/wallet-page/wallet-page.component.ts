import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ActivatedRoute, RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { InputNumberModule } from "primeng/inputnumber";

import { PaymentSheetComponent } from "~/passenger/payment/components/payment-sheet/payment-sheet.component";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";

import { WalletService } from "../../services/wallet.service";

@Component({
  selector: "app-wallet-page",
  standalone: true,
  imports: [
    RouterModule,
    FormsModule,
    ButtonModule,
    InputNumberModule,
    TopBarComponent,
    BackButtonComponent,
    PaymentSheetComponent,
  ],
  templateUrl: "./wallet-page.component.html",
  styleUrl: "./wallet-page.component.css",
})
export class WalletPageComponent {
  protected readonly balance;

  protected rechargeAmountPln = 10;

  public constructor(
    protected readonly i18n: I18nService,
    activatedRoute: ActivatedRoute,
  ) {
    const balanceGrosze = activatedRoute.snapshot.data["balance"];
    this.balance = `${(balanceGrosze / 100).toFixed(2)} ${WalletService.currency}`;
  }
}
