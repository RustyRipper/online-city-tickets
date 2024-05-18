import { Injectable } from "@angular/core";
import { BehaviorSubject, EMPTY, catchError } from "rxjs";

import type { TicketOfferDto } from "~/generated/api/models";
import { OffersApi } from "~/generated/api/services";
import { StoredCell } from "~/shared/store/stored-cell";

export type Offer = TicketOfferDto;

@Injectable({
  providedIn: "root",
})
export class OffersService {
  private readonly offersSubject = new BehaviorSubject<Offer[]>([]);
  public readonly offers$ = this.offersSubject.asObservable();

  public readonly preferredKindCell;

  public constructor(
    private readonly offersApi: OffersApi,
    storage: Storage,
  ) {
    this.preferredKindCell = StoredCell.of(storage, "TICKET_KIND", "standard");
  }

  public revalidateOffers(): void {
    this.offersApi
      .listTicketOffers()
      .pipe(catchError(() => EMPTY))
      .subscribe((v) => this.offersSubject.next(v));
  }
}
