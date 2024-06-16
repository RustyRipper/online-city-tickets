/* tslint:disable */
/* eslint-disable */
import { Cvc } from '../models/cvc';
import { Id } from '../models/id';
export interface RechargeWithSavedCreditCardReq {
  amountGrosze: number;
  creditCardId: Id;
  cvc: Cvc;
}
