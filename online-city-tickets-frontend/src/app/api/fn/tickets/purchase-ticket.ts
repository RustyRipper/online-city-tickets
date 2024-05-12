/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PurchaseTicketReq } from '../../models/purchase-ticket-req';
import { TicketDto } from '../../models/ticket-dto';

export interface PurchaseTicket$Params {
      body: PurchaseTicketReq
}

export function purchaseTicket(http: HttpClient, rootUrl: string, params: PurchaseTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<TicketDto>> {
  const rb = new RequestBuilder(rootUrl, purchaseTicket.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
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

purchaseTicket.PATH = '/tickets';
