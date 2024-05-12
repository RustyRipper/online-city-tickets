/* tslint:disable */
/* eslint-disable */
import { BaseAccountDto } from '../models/base-account-dto';
import { Id } from '../models/id';
import { PhoneNumber } from '../models/phone-number';
export type PassengerDto = BaseAccountDto & {
'type': 'passenger';
'walletBalanceGrosze': number;
'phoneNumber'?: PhoneNumber;
'defaultCreditCardId'?: Id;
};
