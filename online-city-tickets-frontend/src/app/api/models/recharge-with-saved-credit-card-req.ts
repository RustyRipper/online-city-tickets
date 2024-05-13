/* tslint:disable */
/* eslint-disable */
import { Csc } from '../models/csc';
import { Id } from '../models/id';
export interface RechargeWithSavedCreditCardReq {
  amountGrosze: number;
  creditCardId: Id;
  csc: Csc;
}
