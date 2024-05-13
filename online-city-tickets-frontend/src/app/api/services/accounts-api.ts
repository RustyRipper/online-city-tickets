/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { AccountDto } from '../models/account-dto';
import { getAccount } from '../fn/accounts/get-account';
import { GetAccount$Params } from '../fn/accounts/get-account';
import { updateAccount } from '../fn/accounts/update-account';
import { UpdateAccount$Params } from '../fn/accounts/update-account';


/**
 * Operations related to user accounts.
 */
@Injectable({ providedIn: 'root' })
export class AccountsApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAccount()` */
  static readonly GetAccountPath = '/account';

  /**
   * Get current account's details.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAccount()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAccount$Response(params?: GetAccount$Params, context?: HttpContext): Observable<StrictHttpResponse<AccountDto>> {
    return getAccount(this.http, this.rootUrl, params, context);
  }

  /**
   * Get current account's details.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAccount$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAccount(params?: GetAccount$Params, context?: HttpContext): Observable<AccountDto> {
    return this.getAccount$Response(params, context).pipe(
      map((r: StrictHttpResponse<AccountDto>): AccountDto => r.body)
    );
  }

  /** Path part for operation `updateAccount()` */
  static readonly UpdateAccountPath = '/account';

  /**
   * Update current account's details.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateAccount()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateAccount$Response(params: UpdateAccount$Params, context?: HttpContext): Observable<StrictHttpResponse<AccountDto>> {
    return updateAccount(this.http, this.rootUrl, params, context);
  }

  /**
   * Update current account's details.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateAccount$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateAccount(params: UpdateAccount$Params, context?: HttpContext): Observable<AccountDto> {
    return this.updateAccount$Response(params, context).pipe(
      map((r: StrictHttpResponse<AccountDto>): AccountDto => r.body)
    );
  }

}
