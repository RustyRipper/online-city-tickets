import type { TicketDto, ValidationDto } from "~/generated/api/models";

import type { Offer } from "../offers/types";

export type TicketStatus = "unvalidated" | "active" | "archived";

export class Ticket {
  private static readonly SF_DURATION_MS = 4 * 60 * 60 * 1000;

  private constructor(
    public readonly code: string,
    public readonly offer: Offer,
    public readonly purchaseTime: Date,
    private readonly validation?: ValidationDto,
  ) {}

  public get validFrom(): Date | null {
    if (this.offer.scope === "single-fare") {
      return this.validation ? new Date(this.validation.time) : null;
    }
    return this.purchaseTime;
  }

  public get validUntil(): Date | null {
    if (this.offer.scope === "single-fare" && !this.validation) {
      return null;
    }

    const [start, duration] = (() => {
      switch (this.offer.scope) {
        case "single-fare":
          return [this.validFrom!, Ticket.SF_DURATION_MS];
        case "time-limited":
          return [this.purchaseTime, this.offer.durationMinutes * 60 * 1000];
        case "long-term":
          return [
            this.purchaseTime,
            this.offer.validDays * 24 * 60 * 60 * 1000,
          ];
      }
    })();

    return new Date(start.getTime() + duration);
  }

  public get status(): TicketStatus {
    if (this.validFrom === null || this.validUntil === null) {
      return "unvalidated";
    }

    const now = new Date();
    if (this.validFrom <= now && now <= this.validUntil) {
      return "active";
    }

    return "archived";
  }

  public static deserialize(dto: TicketDto): Ticket {
    return new Ticket(
      dto.code,
      dto.offer,
      new Date(dto.purchaseTime),
      dto.validation,
    );
  }
}
