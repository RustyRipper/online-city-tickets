/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TicketCode } from '../../models/ticket-code';
import { TicketDto } from '../../models/ticket-dto';

export interface GetTicket$Params {

/**
 * Unique code of the ticket.
 */
  code: TicketCode;
}

export function getTicket(http: HttpClient, rootUrl: string, params: GetTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<TicketDto>> {
  const rb = new RequestBuilder(rootUrl, getTicket.PATH, 'get');
  if (params) {
    rb.path('code', params.code, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<TicketDto>;
    })
  );
}

getTicket.PATH = '/tickets/{code}';
