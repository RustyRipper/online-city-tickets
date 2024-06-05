import { Injectable } from "@angular/core";
import { BehaviorSubject, EMPTY, catchError } from "rxjs";

import { CardsApi } from "~/generated/api/services";
import type { CreditCard } from "~/passenger/credit-cards/types";

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
      .subscribe((v) => this.cardsSubject.next(v.sort((a, b) => a.id - b.id)));
  }
}
