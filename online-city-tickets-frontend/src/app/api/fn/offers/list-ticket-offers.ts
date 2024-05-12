/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TicketOfferDto } from '../../models/ticket-offer-dto';

export interface ListTicketOffers$Params {
}

export function listTicketOffers(http: HttpClient, rootUrl: string, params?: ListTicketOffers$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<TicketOfferDto>>> {
  const rb = new RequestBuilder(rootUrl, listTicketOffers.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<TicketOfferDto>>;
    })
  );
}

listTicketOffers.PATH = '/offers';
