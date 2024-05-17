/* tslint:disable */
/* eslint-disable */
import { FullName } from '../models/full-name';
import { Password } from '../models/password';
import { PhoneNumber } from '../models/phone-number';
export interface UpdateAccountReq {
  fullName?: FullName;
  newPassword?: Password;
  phoneNumber?: PhoneNumber;
}
