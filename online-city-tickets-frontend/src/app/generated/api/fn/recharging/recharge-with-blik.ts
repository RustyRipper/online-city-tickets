/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RechargeDto } from '../../models/recharge-dto';
import { RechargeWithBlikReq } from '../../models/recharge-with-blik-req';

export interface RechargeWithBlik$Params {
      body: RechargeWithBlikReq
}

export function rechargeWithBlik(http: HttpClient, rootUrl: string, params: RechargeWithBlik$Params, context?: HttpContext): Observable<StrictHttpResponse<RechargeDto>> {
  const rb = new RequestBuilder(rootUrl, rechargeWithBlik.PATH, 'post');
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

rechargeWithBlik.PATH = '/recharge/blik';
