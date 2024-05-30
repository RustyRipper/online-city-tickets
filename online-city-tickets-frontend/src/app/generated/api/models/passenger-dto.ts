/* tslint:disable */
/* eslint-disable */
import { BaseAccountDto } from '../models/base-account-dto';
import { PhoneNumber } from '../models/phone-number';
export type PassengerDto = BaseAccountDto & {
'type': 'passenger';
'walletBalanceGrosze': number;
'phoneNumber'?: PhoneNumber;
};
