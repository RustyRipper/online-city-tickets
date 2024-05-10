/* tslint:disable */
/* eslint-disable */
import { CreditCardNumber } from '../models/credit-card-number';
import { ExpirationDate } from '../models/expiration-date';
import { FullName } from '../models/full-name';
export interface SaveCreditCardReq {
  expirationDate: ExpirationDate;
  holderName: FullName;
  label?: string;
  number: CreditCardNumber;
}
