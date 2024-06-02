import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";

import { I18nService } from "~/shared/i81n/i18n.service";

import { Ticket, TicketStatus } from "../../model";
import { TicketsService } from "../../services/tickets.service";
import { TicketCardComponent } from "../ticket-card/ticket-card.component";

type StatusList = { [status in TicketStatus]: Ticket[] };

@Component({
  selector: "app-tickets-page",
  standalone: true,
  imports: [RouterModule, ButtonModule, TicketCardComponent],
  templateUrl: "./tickets-page.component.html",
  styleUrl: "./tickets-page.component.css",
})
export class TicketsPageComponent implements OnInit {
  protected readonly statuses = ["unvalidated", "active", "archived"] as const;
  protected tickets: StatusList = { unvalidated: [], active: [], archived: [] };

  public constructor(
    private readonly ticketsService: TicketsService,
    protected readonly i18n: I18nService,
  ) {}

  protected get isEmpty(): boolean {
    return Object.values(this.tickets).every((tickets) => tickets.length === 0);
  }

  protected groupLabel(status: TicketStatus): string {
    return this.i18n.t(`tickets-page.${status}`);
  }

  public ngOnInit(): void {
    this.ticketsService.tickets$.subscribe((tickets) => {
      tickets.sort(
        (a, b) => a.purchaseTime.getTime() - b.purchaseTime.getTime(),
      );

      this.tickets = Object.fromEntries(
        this.statuses.map((status) => [
          status,
          tickets.filter((t) => t.status === status),
        ]),
      ) as StatusList;
    });
    this.ticketsService.revalidateTickets();
  }
}
