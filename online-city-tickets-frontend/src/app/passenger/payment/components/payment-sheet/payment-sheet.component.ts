import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import { WalletService } from "~/passenger/wallet/services/wallet.service";

type PaymentId = "wallet" | "blik" | "new-credit-card" | `credit-card${number}`;
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

  protected allowedPaymentIds: PaymentId[] = [
    "wallet",
    "new-credit-card",
    "blik",
  ];
  protected paymentId: PaymentId = this.allowedPaymentIds[0];

  public constructor(private readonly walletService: WalletService) {}

  public ngOnInit(): void {
    this.walletService.balanceGrosze$.subscribe((balanceGrosze) => {
      this.walletBalance = `${(balanceGrosze / 100).toFixed(2)} ${WalletService.currency}`;
    });
    this.walletService.revalidateBalanceGrosze();
  }

  protected method(id: PaymentId): PaymentMethod {
    switch (id) {
      case "wallet":
        return {
          id: "wallet",
          name: `Wallet (${this.walletBalance})`,
          icon: "pi-wallet",
        };
      case "blik":
        return {
          id: "blik",
          name: "BLIK",
          icon: "pi-mobile",
        };
      case "new-credit-card":
        return {
          id: "new-credit-card",
          name: "New credit card",
          icon: "pi-credit-card",
        };
    }

    throw new Error("unimplemented");
  }
}
