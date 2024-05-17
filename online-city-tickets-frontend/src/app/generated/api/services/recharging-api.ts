/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { RechargeDto } from '../models/recharge-dto';
import { rechargeWithBlik } from '../fn/recharging/recharge-with-blik';
import { RechargeWithBlik$Params } from '../fn/recharging/recharge-with-blik';
import { rechargeWithNewCreditCard } from '../fn/recharging/recharge-with-new-credit-card';
import { RechargeWithNewCreditCard$Params } from '../fn/recharging/recharge-with-new-credit-card';
import { rechargeWithSavedCreditCard } from '../fn/recharging/recharge-with-saved-credit-card';
import { RechargeWithSavedCreditCard$Params } from '../fn/recharging/recharge-with-saved-credit-card';


/**
 * Operations related to the virtual wallet recharging.
 */
@Injectable({ providedIn: 'root' })
export class RechargingApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `rechargeWithNewCreditCard()` */
  static readonly RechargeWithNewCreditCardPath = '/recharge/new-credit-card';

  /**
   * Recharge the virtual wallet using a new credit card.
   *
   * This operation does not save the credit card on the account.<br/>Use the `/account/credit-cards` endpoint instead.<br/>This operation is propagated to the payment service.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `rechargeWithNewCreditCard()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  rechargeWithNewCreditCard$Response(params: RechargeWithNewCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<RechargeDto>> {
    return rechargeWithNewCreditCard(this.http, this.rootUrl, params, context);
  }

  /**
   * Recharge the virtual wallet using a new credit card.
   *
   * This operation does not save the credit card on the account.<br/>Use the `/account/credit-cards` endpoint instead.<br/>This operation is propagated to the payment service.
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `rechargeWithNewCreditCard$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  rechargeWithNewCreditCard(params: RechargeWithNewCreditCard$Params, context?: HttpContext): Observable<RechargeDto> {
    return this.rechargeWithNewCreditCard$Response(params, context).pipe(
      map((r: StrictHttpResponse<RechargeDto>): RechargeDto => r.body)
    );
  }

  /** Path part for operation `rechargeWithSavedCreditCard()` */
  static readonly RechargeWithSavedCreditCardPath = '/recharge/saved-credit-card';

  /**
   * Recharge the virtual wallet using a saved credit card.
   *
   * This operation is propagated to the payment service.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `rechargeWithSavedCreditCard()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  rechargeWithSavedCreditCard$Response(params: RechargeWithSavedCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<RechargeDto>> {
    return rechargeWithSavedCreditCard(this.http, this.rootUrl, params, context);
  }

  /**
   * Recharge the virtual wallet using a saved credit card.
   *
   * This operation is propagated to the payment service.
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `rechargeWithSavedCreditCard$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  rechargeWithSavedCreditCard(params: RechargeWithSavedCreditCard$Params, context?: HttpContext): Observable<RechargeDto> {
    return this.rechargeWithSavedCreditCard$Response(params, context).pipe(
      map((r: StrictHttpResponse<RechargeDto>): RechargeDto => r.body)
    );
  }

  /** Path part for operation `rechargeWithBlik()` */
  static readonly RechargeWithBlikPath = '/recharge/blik';

  /**
   * Recharge the virtual wallet using BLIK.
   *
   * This operation is propagated to the payment service.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `rechargeWithBlik()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  rechargeWithBlik$Response(params: RechargeWithBlik$Params, context?: HttpContext): Observable<StrictHttpResponse<RechargeDto>> {
    return rechargeWithBlik(this.http, this.rootUrl, params, context);
  }

  /**
   * Recharge the virtual wallet using BLIK.
   *
   * This operation is propagated to the payment service.
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `rechargeWithBlik$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  rechargeWithBlik(params: RechargeWithBlik$Params, context?: HttpContext): Observable<RechargeDto> {
    return this.rechargeWithBlik$Response(params, context).pipe(
      map((r: StrictHttpResponse<RechargeDto>): RechargeDto => r.body)
    );
  }

}
