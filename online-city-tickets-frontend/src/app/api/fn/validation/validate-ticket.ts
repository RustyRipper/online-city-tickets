/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { TicketCode } from '../../models/ticket-code';
import { ValidateTicketReq } from '../../models/validate-ticket-req';
import { ValidationDto } from '../../models/validation-dto';

export interface ValidateTicket$Params {

/**
 * Unique code of the ticket.
 */
  code: TicketCode;
      body: ValidateTicketReq
}

export function validateTicket(http: HttpClient, rootUrl: string, params: ValidateTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<ValidationDto>> {
  const rb = new RequestBuilder(rootUrl, validateTicket.PATH, 'post');
  if (params) {
    rb.path('code', params.code, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ValidationDto>;
    })
  );
}

validateTicket.PATH = '/tickets/{code}/validate';
