/* tslint:disable */
/* eslint-disable */
import { CreditCardNumber } from '../models/credit-card-number';
import { Csc } from '../models/csc';
import { ExpirationDate } from '../models/expiration-date';
import { FullName } from '../models/full-name';
export interface RechargeWithNewCreditCardReq {
  amountGrosze: number;
  csc: Csc;
  expirationDate: ExpirationDate;
  holderName: FullName;
  number: CreditCardNumber;
}
