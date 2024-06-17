import { Component, Input } from "@angular/core";

import { I18nService } from "~/shared/i18n/i18n.service";

type CardProvider = "placeholder" | "mastercard" | "amex" | "visa" | "discover";

@Component({
  selector: "app-card-display",
  standalone: true,
  imports: [],
  templateUrl: "./card-display.component.html",
  styleUrl: "./card-display.component.css",
})
export class CardDisplayComponent {
  @Input({ required: true }) public number!: string;
  @Input({ required: true }) public holderName!: string;
  @Input({ required: true }) public expirationDate!: string;

  public constructor(private readonly i18n: I18nService) {}

  protected get numberDisplay(): string {
    const template = "•••• •••• •••• ••••";
    const number = this.number.replaceAll("_", "•");
    return number + template.slice(number.length);
  }

  protected get holderNameDisplay(): string {
    return this.holderName.length > 0
      ? this.holderName
      : this.i18n.t("card-display.holder-name-placeholder");
  }

  protected get expirationDateDisplay(): string {
    const template = this.i18n.t("card-display.expiration-date-template");
    return this.expirationDate + template.slice(this.expirationDate.length);
  }

  protected get cardProvider(): CardProvider {
    if (this.number.length < 1 || isNaN(parseInt(this.number[0], 10))) {
      return "placeholder";
    }
    const digit = parseInt(this.number[0], 10);
    switch (digit) {
      case 2:
      case 5:
        return "mastercard";
      case 3:
        return "amex";
      case 4:
        return "visa";
      case 6:
        return "discover";
      default:
        return "placeholder";
    }
  }
}
