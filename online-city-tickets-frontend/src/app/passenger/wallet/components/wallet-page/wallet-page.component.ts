import { Component } from "@angular/core";

import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";

@Component({
  selector: "app-wallet-page",
  standalone: true,
  imports: [TopBarComponent, BackButtonComponent],
  templateUrl: "./wallet-page.component.html",
})
export class WalletPageComponent {}
