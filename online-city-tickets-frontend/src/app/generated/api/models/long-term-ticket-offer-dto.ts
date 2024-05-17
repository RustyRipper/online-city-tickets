/* tslint:disable */
/* eslint-disable */
import { BaseTicketOfferDto } from '../models/base-ticket-offer-dto';
export type LongTermTicketOfferDto = BaseTicketOfferDto & {
'scope': 'long-term';
'intervalInDays': number;
};
