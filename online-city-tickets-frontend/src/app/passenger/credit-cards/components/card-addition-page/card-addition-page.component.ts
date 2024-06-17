import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { InputGroupModule } from "primeng/inputgroup";
import { InputMaskModule } from "primeng/inputmask";
import { StepperModule } from "primeng/stepper";

import { SaveCreditCardReq } from "~/generated/api/models";
import { CardDisplayComponent } from "~/passenger/credit-cards/components/card-display/card-display.component";
import { CreditCardService } from "~/passenger/credit-cards/services/credit-card.service";
import { BackButtonComponent } from "~/shared/components/back-button/back-button.component";
import { TopBarComponent } from "~/shared/components/top-bar/top-bar.component";
import { I18nService } from "~/shared/i18n/i18n.service";

class InvalidCardError extends Error {
  private constructor() {
    super();
  }
  public static raise(): never {
    throw new InvalidCardError();
  }
}

type ParserName = {
  [K in keyof InstanceType<typeof CardAdditionPageComponent>]: InstanceType<
    typeof CardAdditionPageComponent
  >[K] extends () => unknown
    ? K
    : never;
}[keyof InstanceType<typeof CardAdditionPageComponent>];

@Component({
  selector: "app-card-addition-page",
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    InputGroupModule,
    InputMaskModule,
    StepperModule,
    TopBarComponent,
    BackButtonComponent,
    CardDisplayComponent,
  ],
  templateUrl: "./card-addition-page.component.html",
  styleUrl: "./card-addition-page.component.css",
})
export class CardAdditionPageComponent {
  protected number: string = "";
  protected holderName: string = "";
  protected expirationDate: string = "";
  protected label: string = "";

  protected error: string = "";
  protected isLoading: boolean = false;

  public constructor(
    private readonly creditCardService: CreditCardService,
    private readonly router: Router,
    protected readonly i18n: I18nService,
  ) {}

  protected fails(parserName: ParserName): boolean {
    const parse = this[parserName].bind(this);
    try {
      parse();
      return false;
    } catch (err) {
      if (err instanceof InvalidCardError) {
        return true;
      }
      throw err;
    }
  }

  public parseNumber(): string {
    const number = this.number.replace(/[^0-9]/g, "");
    if (number.length < 16) {
      InvalidCardError.raise();
    }

    // Luhn's algorithm
    const digits = number.split("").map(Number);
    let sum = 0;
    let isSecond = false;
    for (let i = digits.length - 1; i >= 0; i--) {
      let digit = digits[i];
      if (isSecond) {
        digit *= 2;
        if (digit > 9) {
          digit -= 9;
        }
      }
      sum += digit;
      isSecond = !isSecond;
    }
    if (sum % 10 !== 0) {
      InvalidCardError.raise();
    }

    return number;
  }

  public parseHolderName(): string {
    if (this.holderName.length < 1 || this.holderName.length > 70) {
      InvalidCardError.raise();
    }
    return this.holderName;
  }

  public parseExpirationDate(): string {
    if (!/^(0[1-9]|1[0-2])\/[0-9]{2}$/.test(this.expirationDate)) {
      InvalidCardError.raise();
    }
    return this.expirationDate;
  }

  public parseLabel(): string | undefined {
    if (this.label.length === 0) {
      return undefined;
    }
    if (this.label.length > 50) {
      InvalidCardError.raise();
    }
    return this.label;
  }

  public parseForm(): SaveCreditCardReq {
    return {
      number: this.parseNumber(),
      holderName: this.parseHolderName(),
      expirationDate: this.parseExpirationDate(),
      label: this.parseLabel(),
    };
  }

  protected async onSubmit(): Promise<void> {
    if (this.fails("parseForm")) {
      return;
    }
    const body = this.parseForm();

    try {
      this.error = "";
      this.isLoading = true;

      await this.creditCardService.addCard(body);

      this.router.navigateByUrl("/passenger/credit-cards");
    } catch {
      this.error = this.i18n.t("card-addition-page.error");
    } finally {
      this.isLoading = false;
    }
  }
}
