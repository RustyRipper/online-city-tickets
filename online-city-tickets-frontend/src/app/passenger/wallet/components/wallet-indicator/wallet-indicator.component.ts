import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ChipModule } from "primeng/chip";
import { AccountsApi } from "../../../../api/services";
import { AuthService } from "../../../../auth/services/auth.service";

@Component({
  selector: "app-wallet-indicator",
  standalone: true,
  imports: [RouterModule, ChipModule],
  templateUrl: "./wallet-indicator.component.html",
  styleUrl: "./wallet-indicator.component.css",
})
export class WalletIndicatorComponent implements OnInit {
  private static readonly currency = "zł";
  private balanceGrosze = 0;

  public constructor(private readonly accountsApi: AccountsApi) {}

  protected get label(): string {
    return `${(this.balanceGrosze / 100).toFixed(2)} ${WalletIndicatorComponent.currency}`;
  }

  public ngOnInit(): void {
    this.accountsApi.getAccount().subscribe((account) => {
      // FIXME: remove this once backend is updated
      account = AuthService.fixAccountObject(account);
      if (account.type === "passenger") {
        this.balanceGrosze = account.walletBalanceGrosze;
      }
    });
  }
}
