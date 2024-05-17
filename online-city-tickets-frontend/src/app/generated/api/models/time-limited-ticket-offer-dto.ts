/* tslint:disable */
/* eslint-disable */
import { BaseTicketOfferDto } from '../models/base-ticket-offer-dto';
export type TimeLimitedTicketOfferDto = BaseTicketOfferDto & {
'scope': 'time-limited';
'durationMinutes': number;
};
