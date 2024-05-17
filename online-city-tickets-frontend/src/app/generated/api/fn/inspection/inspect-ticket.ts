/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { InspectTicketReq } from '../../models/inspect-ticket-req';
import { InspectTicketRes } from '../../models/inspect-ticket-res';
import { TicketCode } from '../../models/ticket-code';

export interface InspectTicket$Params {

/**
 * Unique code of the ticket.
 */
  code: TicketCode;
      body: InspectTicketReq
}

export function inspectTicket(http: HttpClient, rootUrl: string, params: InspectTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<InspectTicketRes>> {
  const rb = new RequestBuilder(rootUrl, inspectTicket.PATH, 'post');
  if (params) {
    rb.path('code', params.code, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<InspectTicketRes>;
    })
  );
}

inspectTicket.PATH = '/tickets/{code}/inspect';
