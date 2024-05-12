/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PassengerDto } from '../../models/passenger-dto';
import { RegisterAsPassengerReq } from '../../models/register-as-passenger-req';

export interface RegisterAsPassenger$Params {
      body: RegisterAsPassengerReq
}

export function registerAsPassenger(http: HttpClient, rootUrl: string, params: RegisterAsPassenger$Params, context?: HttpContext): Observable<StrictHttpResponse<PassengerDto>> {
  const rb = new RequestBuilder(rootUrl, registerAsPassenger.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PassengerDto>;
    })
  );
}

registerAsPassenger.PATH = '/auth/register/passenger';
