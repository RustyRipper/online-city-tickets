import { Component, Input, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { DialogModule } from "primeng/dialog";
import { DropdownModule } from "primeng/dropdown";
import { InputOtpModule } from "primeng/inputotp";

import type { CreditCard } from "~/passenger/credit-cards/model";
import { CreditCardService } from "~/passenger/credit-cards/services/credit-card.service";
import type { PaymentId } from "~/passenger/payment/types";
import { WalletService } from "~/passenger/wallet/services/wallet.service";
import { I18nService } from "~/shared/i18n/i18n.service";

type PaymentMethod = { id: PaymentId; name: string; icon: string };
type State = "idle" | "finalizing" | "recharging" | "purchasing";

@Component({
  selector: "app-payment-sheet",
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    DropdownModule,
    DialogModule,
    InputOtpModule,
  ],
  templateUrl: "./payment-sheet.component.html",
  styleUrl: "./payment-sheet.component.css",
})
export class PaymentSheetComponent implements OnInit {
  private walletBalanceGrosze = 0;
  private creditCards: CreditCard[] = [];

  protected paymentId: PaymentId = "blik";
  protected state: State = "idle";
  protected otp: string = "";

  @Input({ required: true }) public actionName!: string;
  @Input({ required: true }) public costGrosze!: number;
  @Input() public afterRecharge: (() => Promise<void>) | null = null;

  public constructor(
    private readonly router: Router,
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
      !!this.afterRecharge && this.walletBalanceGrosze >= this.costGrosze
        ? "wallet"
        : "blik";
  }

  protected get allowedPaymentIds(): PaymentId[] {
    return [
      ...(!!this.afterRecharge ? ["wallet" as const] : []),
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

  protected get dialogVisible(): boolean {
    return this.state !== "idle" && this.paymentId !== "wallet";
  }

  protected get dialogHeader(): string {
    if (this.paymentId === "blik") {
      return this.i18n.t("payment-sheet.blik");
    }
    if (this.paymentId.startsWith("card#")) {
      const cardId = parseInt(this.paymentId.slice("card#".length), 10);
      const card = this.creditCards.find((c) => c.id === cardId);
      return card?.labelWithDigits ?? "";
    }

    // Other payment methods lack a dialog
    return "";
  }

  protected onDialogClose(): void {
    if (this.state === "finalizing") {
      this.state = "idle";
    }
  }

  protected get otpLength(): number {
    if (this.paymentId === "blik") {
      return 6;
    }
    if (this.paymentId.startsWith("card#")) {
      return 3;
    }
    return 0;
  }

  protected get finalizeLabel(): string {
    return `${this.actionName} (${this.i18n.currency(this.costGrosze)})`;
  }

  protected get finalizeDisabled(): boolean {
    return (
      this.paymentId === "wallet" && this.walletBalanceGrosze < this.costGrosze
    );
  }

  protected get finalizeLoading(): boolean {
    return this.state !== "idle";
  }

  protected async onFinalizeStart(): Promise<void> {
    this.state = "finalizing";
    this.otp = "";

    if (this.paymentId === "new-card") {
      this.router.navigateByUrl("/passenger/credit-cards/add");
      return;
    }

    if (this.paymentId === "wallet") {
      return await this.onPurchaseStart();
    }
  }

  protected get rechargeDisabled(): boolean {
    return this.otp.length !== this.otpLength;
  }

  protected get rechargeLoading(): boolean {
    return this.state !== "finalizing";
  }

  protected async onRechargeStart(): Promise<void> {
    this.state = "recharging";
  }

  private async onPurchaseStart(): Promise<void> {
    this.state = "purchasing";
  }
}
