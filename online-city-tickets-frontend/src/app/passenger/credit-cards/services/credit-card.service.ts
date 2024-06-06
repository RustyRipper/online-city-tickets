import { Injectable } from "@angular/core";
import { BehaviorSubject, EMPTY, catchError, firstValueFrom } from "rxjs";

import { UpdateCreditCardReq } from "~/generated/api/models";
import { CardsApi } from "~/generated/api/services";
import { CreditCard } from "~/passenger/credit-cards/model";

@Injectable({
  providedIn: "root",
})
export class CreditCardService {
  private readonly cardsSubject = new BehaviorSubject<CreditCard[]>([]);
  public readonly cards$ = this.cardsSubject.asObservable();

  public constructor(private readonly cardsApi: CardsApi) {}

  public revalidateCards(optimisticValue?: CreditCard[]): void {
    if (optimisticValue) {
      this.cardsSubject.next(optimisticValue);
    }
    this.cardsApi
      .listCreditCards()
      .pipe(catchError(() => EMPTY))
      .subscribe((v) =>
        this.cardsSubject.next(
          v.map(CreditCard.deserialize).sort((a, b) => a.id - b.id),
        ),
      );
  }

  public async editCard(id: number, body: UpdateCreditCardReq): Promise<void> {
    const card = await firstValueFrom(
      this.cardsApi.updateCreditCard({ id: String(id), body }),
    );
    this.revalidateCards(
      this.cardsSubject.value.map((c) =>
        c.id === id ? CreditCard.deserialize(card) : c,
      ),
    );
  }

  public async deleteCard(id: number): Promise<void> {
    await firstValueFrom(this.cardsApi.deleteCreditCard({ id: String(id) }));
    this.revalidateCards(this.cardsSubject.value.filter((c) => c.id !== id));
  }
}
