/* tslint:disable */
/* eslint-disable */
import { TicketCode } from '../models/ticket-code';
import { TicketOfferDto } from '../models/ticket-offer-dto';
import { ValidationDto } from '../models/validation-dto';
export interface TicketDto {
  code: TicketCode;
  offer: TicketOfferDto;
  purchaseTime: string;
  validation?: ValidationDto;
}
