/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CreditCardDto } from '../../models/credit-card-dto';

export interface GetCreditCard$Params {

/**
 * Unique identifier of the credit card.
 */
  id: string;
}

export function getCreditCard(http: HttpClient, rootUrl: string, params: GetCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<CreditCardDto>> {
  const rb = new RequestBuilder(rootUrl, getCreditCard.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CreditCardDto>;
    })
  );
}

getCreditCard.PATH = '/credit-cards/{id}';
