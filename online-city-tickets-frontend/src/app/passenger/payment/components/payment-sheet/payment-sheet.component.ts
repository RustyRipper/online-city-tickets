import { Component, Input, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { DropdownModule } from "primeng/dropdown";

import type { CreditCard } from "~/passenger/credit-cards/model";
import { CreditCardService } from "~/passenger/credit-cards/services/credit-card.service";
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
  private walletBalanceGrosze = 0;
  private creditCards: CreditCard[] = [];

  protected paymentId: PaymentId = "blik";

  @Input({ required: true }) public actionName!: string;
  @Input({ required: true }) public costGrosze!: number;
  @Input() public walletEnabled = true;

  public constructor(
    private readonly walletService: WalletService,
    private readonly creditCardService: CreditCardService,
    protected readonly i18n: I18nService,
  ) {}

  public ngOnInit(): void {
    this.walletService.balanceGrosze$.subscribe(
      (v) => (this.walletBalanceGrosze = v),
    );
    this.walletService.revalidateBalanceGrosze();

    this.creditCardService.cards$.subscribe((v) => (this.creditCards = v));
    this.creditCardService.revalidateCards();

    this.paymentId =
      this.walletEnabled && this.walletBalanceGrosze >= this.costGrosze
        ? "wallet"
        : "blik";
  }

  protected get buttonLabel(): string {
    return `${this.actionName} (${this.i18n.currency(this.costGrosze)})`;
  }

  protected get buttonDisabled(): boolean {
    return (
      this.paymentId === "wallet" && this.walletBalanceGrosze < this.costGrosze
    );
  }

  protected get allowedPaymentIds(): PaymentId[] {
    return [
      ...(this.walletEnabled ? ["wallet" as const] : []),
      "blik",
      ...this.creditCards.map((c) => `card#${c.id}` as const),
      "new-card",
    ];
  }

  protected method(id: PaymentId): PaymentMethod {
    switch (id) {
      case "wallet":
        return {
          id: "wallet",
          name: this.i18n.t("payment-sheet.wallet", {
            balance: this.i18n.currency(this.walletBalanceGrosze),
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

    const cardId = parseInt(id.slice("card#".length), 10);
    const card = this.creditCards.find((c) => c.id === cardId);
    return {
      id,
      name: card?.labelWithDigits ?? "",
      icon: "pi-credit-card",
    };
  }
}
