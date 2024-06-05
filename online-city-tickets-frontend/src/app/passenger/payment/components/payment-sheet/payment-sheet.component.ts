import { Component, Input, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import type { PaymentId } from "~/passenger/payment/types";
import { WalletService } from "~/passenger/wallet/services/wallet.service";
import { I18nService } from "~/shared/i81n/i18n.service";

type PaymentMethod = { id: PaymentId; name: string; icon: string };

@Component({
  selector: "app-payment-sheet",
  standalone: true,
  imports: [FormsModule, ButtonModule, DropdownModule],
  templateUrl: "./payment-sheet.component.html",
  styleUrl: "./payment-sheet.component.css",
})
export class PaymentSheetComponent implements OnInit {
  private walletBalance = "N/A";

  protected paymentId: PaymentId = this.allowedPaymentIds[0];

  @Input({ required: true }) public actionName!: string;

  public constructor(
    private readonly walletService: WalletService,
    protected readonly i18n: I18nService,
  ) {}

  public ngOnInit(): void {
    this.walletService.balanceGrosze$.subscribe((balanceGrosze) => {
      this.walletBalance = `${(balanceGrosze / 100).toFixed(2)} ${WalletService.currency}`;
    });
    this.walletService.revalidateBalanceGrosze();
  }

  protected get allowedPaymentIds(): PaymentId[] {
    return ["wallet", "new-card", "blik"];
  }

  protected method(id: PaymentId): PaymentMethod {
    switch (id) {
      case "wallet":
        return {
          id: "wallet",
          name: this.i18n.t("payment-sheet.wallet", {
            balance: this.walletBalance,
          }),
          icon: "pi-wallet",
        };
      case "blik":
        return {
          id: "blik",
          name: this.i18n.t("payment-sheet.blik"),
          icon: "pi-mobile",
        };
      case "new-card":
        return {
          id: "new-card",
          name: this.i18n.t("payment-sheet.new-card"),
          icon: "pi-credit-card",
        };
    }

    const digits = parseInt(id.slice("card#".length), 10);
    return {
      id,
      name: this.i18n.t("payment-sheet.card", { digits }),
      icon: "pi-credit-card",
    };
  }
}
