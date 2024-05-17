/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getTicket } from '../fn/tickets/get-ticket';
import { GetTicket$Params } from '../fn/tickets/get-ticket';
import { listTickets } from '../fn/tickets/list-tickets';
import { ListTickets$Params } from '../fn/tickets/list-tickets';
import { purchaseTicket } from '../fn/tickets/purchase-ticket';
import { PurchaseTicket$Params } from '../fn/tickets/purchase-ticket';
import { TicketDto } from '../models/ticket-dto';


/**
 * Operations related to tickets.
 */
@Injectable({ providedIn: 'root' })
export class TicketsApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `listTickets()` */
  static readonly ListTicketsPath = '/tickets';

  /**
   * List all tickets of the current account.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `listTickets()` instead.
   *
   * This method doesn't expect any request body.
   */
  listTickets$Response(params?: ListTickets$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<TicketDto>>> {
    return listTickets(this.http, this.rootUrl, params, context);
  }

  /**
   * List all tickets of the current account.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `listTickets$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  listTickets(params?: ListTickets$Params, context?: HttpContext): Observable<Array<TicketDto>> {
    return this.listTickets$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<TicketDto>>): Array<TicketDto> => r.body)
    );
  }

  /** Path part for operation `purchaseTicket()` */
  static readonly PurchaseTicketPath = '/tickets';

  /**
   * Purchase a new ticket.
   *
   * This operation charges the ticket price from the virtual wallet.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `purchaseTicket()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  purchaseTicket$Response(params: PurchaseTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<TicketDto>> {
    return purchaseTicket(this.http, this.rootUrl, params, context);
  }

  /**
   * Purchase a new ticket.
   *
   * This operation charges the ticket price from the virtual wallet.
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `purchaseTicket$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  purchaseTicket(params: PurchaseTicket$Params, context?: HttpContext): Observable<TicketDto> {
    return this.purchaseTicket$Response(params, context).pipe(
      map((r: StrictHttpResponse<TicketDto>): TicketDto => r.body)
    );
  }

  /** Path part for operation `getTicket()` */
  static readonly GetTicketPath = '/tickets/{code}';

  /**
   * Get details of a single ticket of the current account.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getTicket()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTicket$Response(params: GetTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<TicketDto>> {
    return getTicket(this.http, this.rootUrl, params, context);
  }

  /**
   * Get details of a single ticket of the current account.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getTicket$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTicket(params: GetTicket$Params, context?: HttpContext): Observable<TicketDto> {
    return this.getTicket$Response(params, context).pipe(
      map((r: StrictHttpResponse<TicketDto>): TicketDto => r.body)
    );
  }

}
