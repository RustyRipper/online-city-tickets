import { Injectable } from "@angular/core";
import { BehaviorSubject, EMPTY, catchError } from "rxjs";

import { CardsApi } from "~/generated/api/services";
import { CreditCard } from "~/passenger/credit-cards/model";

@Injectable({
  providedIn: "root",
})
export class CreditCardService {
  private readonly cardsSubject = new BehaviorSubject<CreditCard[]>([]);
  public readonly cards$ = this.cardsSubject.asObservable();

  public constructor(private readonly cardsApi: CardsApi) {}

  public revalidateCards(): void {
    this.cardsApi
      .listCreditCards()
      .pipe(catchError(() => EMPTY))
      .subscribe((v) =>
        this.cardsSubject.next(
          v.map(CreditCard.deserialize).sort((a, b) => a.id - b.id),
        ),
      );
  }
}
