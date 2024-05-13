/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RechargeDto } from '../../models/recharge-dto';
import { RechargeWithSavedCreditCardReq } from '../../models/recharge-with-saved-credit-card-req';

export interface RechargeWithSavedCreditCard$Params {
      body: RechargeWithSavedCreditCardReq
}

export function rechargeWithSavedCreditCard(http: HttpClient, rootUrl: string, params: RechargeWithSavedCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<RechargeDto>> {
  const rb = new RequestBuilder(rootUrl, rechargeWithSavedCreditCard.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<RechargeDto>;
    })
  );
}

rechargeWithSavedCreditCard.PATH = '/recharge/saved-credit-card';
