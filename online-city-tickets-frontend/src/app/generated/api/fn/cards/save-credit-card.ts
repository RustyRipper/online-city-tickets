/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CreditCardDto } from '../../models/credit-card-dto';
import { SaveCreditCardReq } from '../../models/save-credit-card-req';

export interface SaveCreditCard$Params {
      body: SaveCreditCardReq
}

export function saveCreditCard(http: HttpClient, rootUrl: string, params: SaveCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<CreditCardDto>> {
  const rb = new RequestBuilder(rootUrl, saveCreditCard.PATH, 'post');
  if (params) {
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

saveCreditCard.PATH = '/credit-cards';
