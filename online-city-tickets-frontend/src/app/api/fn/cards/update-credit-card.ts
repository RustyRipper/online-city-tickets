/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CreditCardDto } from '../../models/credit-card-dto';
import { UpdateCreditCardReq } from '../../models/update-credit-card-req';

export interface UpdateCreditCard$Params {

/**
 * Unique identifier of the credit card.
 */
  id: string;
      body: UpdateCreditCardReq
}

export function updateCreditCard(http: HttpClient, rootUrl: string, params: UpdateCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<CreditCardDto>> {
  const rb = new RequestBuilder(rootUrl, updateCreditCard.PATH, 'patch');
  if (params) {
    rb.path('id', params.id, {});
    rb.body(params.body, 'application/json');
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

updateCreditCard.PATH = '/credit-cards/{id}';
