/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CreditCardDto } from '../../models/credit-card-dto';

export interface ListCreditCards$Params {
}

export function listCreditCards(http: HttpClient, rootUrl: string, params?: ListCreditCards$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<CreditCardDto>>> {
  const rb = new RequestBuilder(rootUrl, listCreditCards.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<CreditCardDto>>;
    })
  );
}

listCreditCards.PATH = '/credit-cards';
