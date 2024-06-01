import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ButtonModule } from "primeng/button";

import { I18nService } from "~/shared/i81n/i18n.service";

import type { Ticket } from "../../model";
import { TicketsService } from "../../services/tickets.service";

@Component({
  selector: "app-tickets-page",
  standalone: true,
  imports: [CommonModule, RouterModule, ButtonModule],
  templateUrl: "./tickets-page.component.html",
  styleUrl: "./tickets-page.component.css",
})
export class TicketsPageComponent implements OnInit {
  protected activeTickets: Ticket[] = [];
  protected inactiveTickets: Ticket[] = [];

  public constructor(
    private readonly ticketsService: TicketsService,
    protected readonly i18n: I18nService,
  ) {}

  public ngOnInit(): void {
    this.ticketsService.tickets$.subscribe((tickets) => {
      tickets.sort(
        (a, b) => a.purchaseTime.getTime() - b.purchaseTime.getTime(),
      );
      this.activeTickets = tickets.filter((t) => t.isActive);
      this.inactiveTickets = tickets.filter((t) => !t.isActive);
    });
    this.ticketsService.revalidateTickets();
  }

  protected stringify = JSON.stringify;
}
