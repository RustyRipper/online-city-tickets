/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RechargeDto } from '../../models/recharge-dto';
import { RechargeWithNewCreditCardReq } from '../../models/recharge-with-new-credit-card-req';

export interface RechargeWithNewCreditCard$Params {
      body: RechargeWithNewCreditCardReq
}

export function rechargeWithNewCreditCard(http: HttpClient, rootUrl: string, params: RechargeWithNewCreditCard$Params, context?: HttpContext): Observable<StrictHttpResponse<RechargeDto>> {
  const rb = new RequestBuilder(rootUrl, rechargeWithNewCreditCard.PATH, 'post');
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

rechargeWithNewCreditCard.PATH = '/recharge/new-credit-card';
