import { Component, type OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ChipModule } from "primeng/chip";

import { WalletService } from "~/passenger/wallet/services/wallet.service";
import { I18nService } from "~/shared/i18n/i18n.service";

@Component({
  selector: "app-wallet-indicator",
  standalone: true,
  imports: [RouterModule, ChipModule],
  templateUrl: "./wallet-indicator.component.html",
  styleUrl: "./wallet-indicator.component.css",
})
export class WalletIndicatorComponent implements OnInit {
  private balanceGrosze = 0;

  public constructor(
    private readonly walletService: WalletService,
    private readonly i18n: I18nService,
  ) {}

  public get label(): string {
    return this.i18n.currency(this.balanceGrosze);
  }

  public ngOnInit(): void {
    this.walletService.balanceGrosze$.subscribe(
      (v) => (this.balanceGrosze = v),
    );
    this.walletService.revalidateBalanceGrosze();
  }
}
