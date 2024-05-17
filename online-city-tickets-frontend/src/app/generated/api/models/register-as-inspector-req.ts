/* tslint:disable */
/* eslint-disable */
import { FullName } from '../models/full-name';
import { Password } from '../models/password';
export interface RegisterAsInspectorReq {
  email: string;
  fullName: FullName;
  password: Password;
}
