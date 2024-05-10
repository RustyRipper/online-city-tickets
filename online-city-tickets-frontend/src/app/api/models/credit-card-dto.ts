/* tslint:disable */
/* eslint-disable */
import { ExpirationDate } from '../models/expiration-date';
import { FullName } from '../models/full-name';
import { Id } from '../models/id';
export interface CreditCardDto {
  expirationDate: ExpirationDate;
  holderName: FullName;
  id: Id;
  label?: string;
  lastFourDigits: string;
}
