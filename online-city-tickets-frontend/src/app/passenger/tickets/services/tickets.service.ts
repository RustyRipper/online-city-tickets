import { Injectable } from "@angular/core";
import { BehaviorSubject, EMPTY, catchError, firstValueFrom } from "rxjs";

import { TicketsApi } from "~/generated/api/services";
import { Ticket } from "~/passenger/tickets/model";

@Injectable({
  providedIn: "root",
})
export class TicketsService {
  private readonly ticketsSubject = new BehaviorSubject<Ticket[]>([]);
  public readonly tickets$ = this.ticketsSubject.asObservable();

  public constructor(private readonly ticketsApi: TicketsApi) {}

  public async purchaseTicket(offerId: number): Promise<Ticket> {
    const dto = await firstValueFrom(
      this.ticketsApi.purchaseTicket({ body: { offerId } }),
    );
    this.revalidateTickets();
    return Ticket.deserialize(dto);
  }

  public revalidateTickets(): void {
    this.ticketsApi
      .listTickets()
      .pipe(catchError(() => EMPTY))
      .subscribe((v) => this.ticketsSubject.next(v.map(Ticket.deserialize)));
  }
}
