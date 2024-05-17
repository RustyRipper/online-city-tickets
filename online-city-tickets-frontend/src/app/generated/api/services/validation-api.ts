/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { validateTicket } from '../fn/validation/validate-ticket';
import { ValidateTicket$Params } from '../fn/validation/validate-ticket';
import { ValidationDto } from '../models/validation-dto';


/**
 * Operations related to ticket validation.
 */
@Injectable({ providedIn: 'root' })
export class ValidationApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `validateTicket()` */
  static readonly ValidateTicketPath = '/tickets/{code}/validate';

  /**
   * Validate a ticket.
   *
   * Ensures that a single-fare or time-limited ticket becomes valid.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `validateTicket()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  validateTicket$Response(params: ValidateTicket$Params, context?: HttpContext): Observable<StrictHttpResponse<ValidationDto>> {
    return validateTicket(this.http, this.rootUrl, params, context);
  }

  /**
   * Validate a ticket.
   *
   * Ensures that a single-fare or time-limited ticket becomes valid.
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `validateTicket$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  validateTicket(params: ValidateTicket$Params, context?: HttpContext): Observable<ValidationDto> {
    return this.validateTicket$Response(params, context).pipe(
      map((r: StrictHttpResponse<ValidationDto>): ValidationDto => r.body)
    );
  }

}
