/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getTicketOffer } from '../fn/offers/get-ticket-offer';
import { GetTicketOffer$Params } from '../fn/offers/get-ticket-offer';
import { listTicketOffers } from '../fn/offers/list-ticket-offers';
import { ListTicketOffers$Params } from '../fn/offers/list-ticket-offers';
import { TicketOfferDto } from '../models/ticket-offer-dto';


/**
 * Operations related to ticket offers.
 */
@Injectable({ providedIn: 'root' })
export class OffersApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `listTicketOffers()` */
  static readonly ListTicketOffersPath = '/offers';

  /**
   * List all available ticket offers.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `listTicketOffers()` instead.
   *
   * This method doesn't expect any request body.
   */
  listTicketOffers$Response(params?: ListTicketOffers$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<TicketOfferDto>>> {
    return listTicketOffers(this.http, this.rootUrl, params, context);
  }

  /**
   * List all available ticket offers.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `listTicketOffers$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  listTicketOffers(params?: ListTicketOffers$Params, context?: HttpContext): Observable<Array<TicketOfferDto>> {
    return this.listTicketOffers$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<TicketOfferDto>>): Array<TicketOfferDto> => r.body)
    );
  }

  /** Path part for operation `getTicketOffer()` */
  static readonly GetTicketOfferPath = '/offers/{id}';

  /**
   * Get details of a single ticket offer.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getTicketOffer()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTicketOffer$Response(params: GetTicketOffer$Params, context?: HttpContext): Observable<StrictHttpResponse<TicketOfferDto>> {
    return getTicketOffer(this.http, this.rootUrl, params, context);
  }

  /**
   * Get details of a single ticket offer.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getTicketOffer$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTicketOffer(params: GetTicketOffer$Params, context?: HttpContext): Observable<TicketOfferDto> {
    return this.getTicketOffer$Response(params, context).pipe(
      map((r: StrictHttpResponse<TicketOfferDto>): TicketOfferDto => r.body)
    );
  }

}
