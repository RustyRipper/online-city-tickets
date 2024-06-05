import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";

import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i81n/i18n.service";

import { CreditCardService } from "../../services/credit-card.service";
import type { CreditCard } from "../../types";

@Component({
  selector: "app-card-list-page",
  standalone: true,
  imports: [
    RouterModule,
    CardModule,
    ButtonModule,
    TopBarComponent,
    BackButtonComponent,
  ],
  templateUrl: "./card-list-page.component.html",
  styleUrl: "./card-list-page.component.css",
})
export class CardListPageComponent implements OnInit {
  protected cards: CreditCard[] = [];

  public constructor(
    private readonly creditCardService: CreditCardService,
    protected readonly i18n: I18nService,
  ) {}

  public ngOnInit(): void {
    this.creditCardService.cards$.subscribe((v) => (this.cards = v));
    this.creditCardService.revalidateCards();
  }

  protected cardHeader(card: CreditCard): string {
    return card.label ? card.label : card.holderName.split(" ")[0];
  }

  protected cardNumber(card: CreditCard): string {
    return `∗∗∗∗ ∗∗∗∗ ∗∗∗∗ ${card.lastFourDigits}`;
  }
}
