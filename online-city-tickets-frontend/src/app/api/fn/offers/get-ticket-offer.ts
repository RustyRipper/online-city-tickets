/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TicketOfferDto } from '../../models/ticket-offer-dto';

export interface GetTicketOffer$Params {

/**
 * Unique identifier of the offer.
 */
  id: string;
}

export function getTicketOffer(http: HttpClient, rootUrl: string, params: GetTicketOffer$Params, context?: HttpContext): Observable<StrictHttpResponse<TicketOfferDto>> {
  const rb = new RequestBuilder(rootUrl, getTicketOffer.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<TicketOfferDto>;
    })
  );
}

getTicketOffer.PATH = '/offers/{id}';
