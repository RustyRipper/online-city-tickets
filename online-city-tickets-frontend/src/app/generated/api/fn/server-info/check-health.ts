/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { HealthDto } from '../../models/health-dto';

export interface CheckHealth$Params {
}

export function checkHealth(http: HttpClient, rootUrl: string, params?: CheckHealth$Params, context?: HttpContext): Observable<StrictHttpResponse<HealthDto>> {
  const rb = new RequestBuilder(rootUrl, checkHealth.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<HealthDto>;
    })
  );
}

checkHealth.PATH = '/actuator/health';
