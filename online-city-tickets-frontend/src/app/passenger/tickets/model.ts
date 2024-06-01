import type { TicketDto, ValidationDto } from "~/generated/api/models";

import type { Offer } from "../offers/types";

export class Ticket {
  private constructor(
    public readonly code: string,
    public readonly offer: Offer,
    public readonly purchaseTime: Date,
    public readonly isActive: boolean,
    private readonly validation?: ValidationDto,
  ) {}

  public static deserialize(dto: TicketDto): Ticket {
    return new Ticket(
      dto.code,
      dto.offer,
      new Date(dto.purchaseTime),
      dto.ticketStatus === "ACTIVE",
      dto.validation,
    );
  }
}
