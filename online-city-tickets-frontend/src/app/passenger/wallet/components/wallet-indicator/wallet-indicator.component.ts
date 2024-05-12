import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ChipModule } from "primeng/chip";
import { AccountsApi } from "../../../../api/services";

@Component({
  selector: "app-wallet-indicator",
  standalone: true,
  imports: [RouterModule, ChipModule],
  templateUrl: "./wallet-indicator.component.html",
  styleUrl: "./wallet-indicator.component.css",
})
export class WalletIndicatorComponent implements OnInit {
  protected static readonly currency = "zł";
  private balanceGrosze = 0;

  public constructor(private readonly accountsApi: AccountsApi) {}

  protected get label(): string {
    return `${(this.balanceGrosze / 100).toFixed(2)} ${WalletIndicatorComponent.currency}`;
  }

  ngOnInit(): void {
    this.accountsApi.getAccount().subscribe((account) => {
      if (account.type === "passenger") {
        this.balanceGrosze = account.walletBalanceGrosze;
      }
    });
  }
}
