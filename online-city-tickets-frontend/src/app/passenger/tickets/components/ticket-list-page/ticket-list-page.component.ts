import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";

import { TicketCardComponent } from "~/passenger/tickets/components/ticket-card/ticket-card.component";
import { Ticket, TicketStatus } from "~/passenger/tickets/model";
import { TicketsService } from "~/passenger/tickets/services/tickets.service";
import { I18nService } from "~/shared/i18n/i18n.service";

type StatusList = { [status in TicketStatus]: Ticket[] };

@Component({
  selector: "app-ticket-list-page",
  standalone: true,
  imports: [RouterModule, ButtonModule, TicketCardComponent],
  templateUrl: "./ticket-list-page.component.html",
  styleUrl: "./ticket-list-page.component.css",
})
export class TicketListPageComponent implements OnInit {
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
    return this.i18n.t(`ticket-list-page.${status}`);
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
