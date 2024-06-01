import { Injectable } from "@angular/core";
import { BehaviorSubject, EMPTY, catchError } from "rxjs";

import { TicketsApi } from "~/generated/api/services";

import type { Ticket } from "../types";

@Injectable({
  providedIn: "root",
})
export class TicketsService {
  private readonly ticketsSubject = new BehaviorSubject<Ticket[]>([]);
  public readonly tickets$ = this.ticketsSubject.asObservable();

  public constructor(private readonly ticketsApi: TicketsApi) {}

  public revalidateTickets(): void {
    this.ticketsApi
      .listTickets()
      .pipe(catchError(() => EMPTY))
      .subscribe((v) => this.ticketsSubject.next(v));
  }
}
