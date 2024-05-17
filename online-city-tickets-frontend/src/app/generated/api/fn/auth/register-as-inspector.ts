/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { InspectorDto } from '../../models/inspector-dto';
import { RegisterAsInspectorReq } from '../../models/register-as-inspector-req';

export interface RegisterAsInspector$Params {
      body: RegisterAsInspectorReq
}

export function registerAsInspector(http: HttpClient, rootUrl: string, params: RegisterAsInspector$Params, context?: HttpContext): Observable<StrictHttpResponse<InspectorDto>> {
  const rb = new RequestBuilder(rootUrl, registerAsInspector.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<InspectorDto>;
    })
  );
}

registerAsInspector.PATH = '/auth/register/inspector';
