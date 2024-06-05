import { CreditCardDto } from "~/generated/api/models";

export class CreditCard {
  private constructor(
    public readonly id: number,
    public readonly lastFourDigits: string,
    public readonly holderName: string,
    public readonly expirationDate: string,
    public readonly customLabel: string | undefined,
  ) {}

  public get label(): string {
    return this.customLabel ? this.customLabel : this.holderName.split(" ")[0];
  }

  public get labelWithDigits(): string {
    return `${this.label} (${this.lastFourDigits})`;
  }

  public get fullNumber(): string {
    return `∗∗∗∗ ∗∗∗∗ ∗∗∗∗ ${this.lastFourDigits}`;
  }

  public static deserialize(dto: CreditCardDto): CreditCard {
    return new CreditCard(
      dto.id,
      dto.lastFourDigits,
      dto.holderName,
      dto.expirationDate,
      dto.label,
    );
  }
}
