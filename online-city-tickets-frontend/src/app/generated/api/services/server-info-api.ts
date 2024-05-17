/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { checkHealth } from '../fn/server-info/check-health';
import { CheckHealth$Params } from '../fn/server-info/check-health';
import { HealthDto } from '../models/health-dto';


/**
 * Operations related to server information.
 */
@Injectable({ providedIn: 'root' })
export class ServerInfoApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `checkHealth()` */
  static readonly CheckHealthPath = '/actuator/health';

  /**
   * Check the health of the server.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `checkHealth()` instead.
   *
   * This method doesn't expect any request body.
   */
  checkHealth$Response(params?: CheckHealth$Params, context?: HttpContext): Observable<StrictHttpResponse<HealthDto>> {
    return checkHealth(this.http, this.rootUrl, params, context);
  }

  /**
   * Check the health of the server.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `checkHealth$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  checkHealth(params?: CheckHealth$Params, context?: HttpContext): Observable<HealthDto> {
    return this.checkHealth$Response(params, context).pipe(
      map((r: StrictHttpResponse<HealthDto>): HealthDto => r.body)
    );
  }

}
