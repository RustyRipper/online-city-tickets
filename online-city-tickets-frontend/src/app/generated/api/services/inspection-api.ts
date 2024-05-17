/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { inspectTicket } from '../fn/inspection/inspect-ticket';
import { InspectTicket$Params } from '../fn/inspection/inspect-ticket';
import { InspectTicketRes } from '../models/inspect-ticket-res';


/**
 * Operations related to ticket inspection.
 */
@Injectable({ providedIn: 'root' })
export class InspectionApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `inspectTicket()` */
  static readonly InspectTicketPath = '/tickets/{code}/inspect';

  /**
   * Inspect a ticket.
   *
   * Checks if the ticket was correctly validated.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `inspectTicket()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  inspectTicket$Response(params: InspectTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<InspectTicketRes>> {
    return inspectTicket(this.http, this.rootUrl, params, context);
  }

  /**
   * Inspect a ticket.
   *
   * Checks if the ticket was correctly validated.
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `inspectTicket$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  inspectTicket(params: InspectTicket$Params, context?: HttpContext): Observable<InspectTicketRes> {
    return this.inspectTicket$Response(params, context).pipe(
      map((r: StrictHttpResponse<InspectTicketRes>): InspectTicketRes => r.body)
    );
  }

}
