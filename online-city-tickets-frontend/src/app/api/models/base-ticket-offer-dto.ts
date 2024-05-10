/* tslint:disable */
/* eslint-disable */
import { Id } from '../models/id';
export interface BaseTicketOfferDto {
  displayNameEn: string;
  displayNamePl: string;
  id: Id;
  kind: 'standard' | 'reduced';
  priceGrosze: number;
}
