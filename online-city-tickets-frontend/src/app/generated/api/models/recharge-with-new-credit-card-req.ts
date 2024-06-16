/* tslint:disable */
/* eslint-disable */
import { CreditCardNumber } from '../models/credit-card-number';
import { Cvc } from '../models/cvc';
import { ExpirationDate } from '../models/expiration-date';
import { FullName } from '../models/full-name';
export interface RechargeWithNewCreditCardReq {
  amountGrosze: number;
  cvc: Cvc;
  expirationDate: ExpirationDate;
  holderName: FullName;
  number: CreditCardNumber;
}
