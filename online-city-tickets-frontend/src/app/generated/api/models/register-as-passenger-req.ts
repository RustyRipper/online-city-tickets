/* tslint:disable */
/* eslint-disable */
import { FullName } from '../models/full-name';
import { Password } from '../models/password';
import { PhoneNumber } from '../models/phone-number';
export interface RegisterAsPassengerReq {
  email: string;
  fullName: FullName;
  password: Password;
  phoneNumber?: PhoneNumber;
}
