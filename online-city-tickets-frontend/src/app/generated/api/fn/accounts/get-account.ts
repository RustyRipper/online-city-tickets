/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AccountDto } from '../../models/account-dto';

export interface GetAccount$Params {
}

export function getAccount(http: HttpClient, rootUrl: string, params?: GetAccount$Params, context?: HttpContext): Observable<StrictHttpResponse<AccountDto>> {
  const rb = new RequestBuilder(rootUrl, getAccount.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<AccountDto>;
    })
  );
}

getAccount.PATH = '/account';
