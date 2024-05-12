/* tslint:disable */
/* eslint-disable */
import { LongTermTicketOfferDto } from '../models/long-term-ticket-offer-dto';
import { SingleFareTicketOfferDto } from '../models/single-fare-ticket-offer-dto';
import { TimeLimitedTicketOfferDto } from '../models/time-limited-ticket-offer-dto';
export type TicketOfferDto = (SingleFareTicketOfferDto | TimeLimitedTicketOfferDto | LongTermTicketOfferDto);
