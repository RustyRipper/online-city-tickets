/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { CreditCardDto } from '../models/credit-card-dto';
import { deleteCreditCard } from '../fn/cards/delete-credit-card';
import { DeleteCreditCard$Params } from '../fn/cards/delete-credit-card';
import { getCreditCard } from '../fn/cards/get-credit-card';
import { GetCreditCard$Params } from '../fn/cards/get-credit-card';
import { listCreditCards } from '../fn/cards/list-credit-cards';
import { ListCreditCards$Params } from '../fn/cards/list-credit-cards';
import { saveCreditCard } from '../fn/cards/save-credit-card';
import { SaveCreditCard$Params } from '../fn/cards/save-credit-card';
import { updateCreditCard } from '../fn/cards/update-credit-card';
import { UpdateCreditCard$Params } from '../fn/cards/update-credit-card';


/**
 * Operations related to credit cards.
 */
@Injectable({ providedIn: 'root' })
export class CardsApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `listCreditCards()` */
  static readonly ListCreditCardsPath = '/credit-cards';

  /**
   * List all saved credit cards of the current account.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `listCreditCards()` instead.
   *
   * This method doesn't expect any request body.
   */
  listCreditCards$Response(params?: ListCreditCards$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<CreditCardDto>>> {
    return listCreditCards(this.http, this.rootUrl, params, context);
  }

  /**
   * List all saved credit cards of the current account.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `listCreditCards$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  listCreditCards(params?: ListCreditCards$Params, context?: HttpContext): Observable<Array<CreditCardDto>> {
    return this.listCreditCards$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<CreditCardDto>>): Array<CreditCardDto> => r.body)
    );
  }

  /** Path part for operation `saveCreditCard()` */
  static readonly SaveCreditCardPath = '/credit-cards';

  /**
   * Save a new credit card on the current account.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `saveCreditCard()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveCreditCard$Response(params: SaveCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<CreditCardDto>> {
    return saveCreditCard(this.http, this.rootUrl, params, context);
  }

  /**
   * Save a new credit card on the current account.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `saveCreditCard$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveCreditCard(params: SaveCreditCard$Params, context?: HttpContext): Observable<CreditCardDto> {
    return this.saveCreditCard$Response(params, context).pipe(
      map((r: StrictHttpResponse<CreditCardDto>): CreditCardDto => r.body)
    );
  }

  /** Path part for operation `getCreditCard()` */
  static readonly GetCreditCardPath = '/credit-cards/{id}';

  /**
   * Get details of a saved credit card.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getCreditCard()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCreditCard$Response(params: GetCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<CreditCardDto>> {
    return getCreditCard(this.http, this.rootUrl, params, context);
  }

  /**
   * Get details of a saved credit card.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getCreditCard$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCreditCard(params: GetCreditCard$Params, context?: HttpContext): Observable<CreditCardDto> {
    return this.getCreditCard$Response(params, context).pipe(
      map((r: StrictHttpResponse<CreditCardDto>): CreditCardDto => r.body)
    );
  }

  /** Path part for operation `deleteCreditCard()` */
  static readonly DeleteCreditCardPath = '/credit-cards/{id}';

  /**
   * Delete a saved credit card.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteCreditCard()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteCreditCard$Response(params: DeleteCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return deleteCreditCard(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete a saved credit card.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteCreditCard$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteCreditCard(params: DeleteCreditCard$Params, context?: HttpContext): Observable<void> {
    return this.deleteCreditCard$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `updateCreditCard()` */
  static readonly UpdateCreditCardPath = '/credit-cards/{id}';

  /**
   * Update details of a saved credit card.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateCreditCard()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateCreditCard$Response(params: UpdateCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<CreditCardDto>> {
    return updateCreditCard(this.http, this.rootUrl, params, context);
  }

  /**
   * Update details of a saved credit card.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateCreditCard$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateCreditCard(params: UpdateCreditCard$Params, context?: HttpContext): Observable<CreditCardDto> {
    return this.updateCreditCard$Response(params, context).pipe(
      map((r: StrictHttpResponse<CreditCardDto>): CreditCardDto => r.body)
    );
  }

}
