import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { InputNumberModule } from "primeng/inputnumber";

import { PaymentSheetComponent } from "~/passenger/payment/components/payment-sheet/payment-sheet.component";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i18n/i18n.service";

import { WalletService } from "../../services/wallet.service";

const DEFAULT_RECHARGE_PLN = 10;

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
export class WalletPageComponent implements OnInit {
  protected balanceGrosze: number = 0;

  protected rechargeAmountPln = DEFAULT_RECHARGE_PLN;

  public constructor(
    private readonly walletService: WalletService,
    protected readonly i18n: I18nService,
  ) {}

  public ngOnInit(): void {
    this.walletService.balanceGrosze$.subscribe(
      (v) => (this.balanceGrosze = v),
    );
    this.walletService.revalidateBalanceGrosze();
  }
}
