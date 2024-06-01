import type { TicketDto, TicketOfferDto } from "~/generated/api/models";

export const MOCK_OFFER = {
  id: 1,
  scope: "single-fare",
  kind: "standard",
  displayNameEn: "Single fare",
  displayNamePl: "Jednorazowy",
  priceGrosze: 100,
} as const satisfies TicketOfferDto;

export const MOCK_TICKET = {
  code: "ABC1234567",
  offer: MOCK_OFFER,
  purchaseTime: "2021-09-01T15:00:00Z",
  validation: {
    time: "2021-09-01T15:10:00Z",
    vehicleSideNumber: "WAW 12345",
  },
} as const satisfies TicketDto;
