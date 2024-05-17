/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { InspectorDto } from '../models/inspector-dto';
import { login } from '../fn/auth/login';
import { Login$Params } from '../fn/auth/login';
import { LoginRes } from '../models/login-res';
import { PassengerDto } from '../models/passenger-dto';
import { registerAsInspector } from '../fn/auth/register-as-inspector';
import { RegisterAsInspector$Params } from '../fn/auth/register-as-inspector';
import { registerAsPassenger } from '../fn/auth/register-as-passenger';
import { RegisterAsPassenger$Params } from '../fn/auth/register-as-passenger';


/**
 * Operations related to authentication.
 */
@Injectable({ providedIn: 'root' })
export class AuthApi extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `registerAsPassenger()` */
  static readonly RegisterAsPassengerPath = '/auth/register/passenger';

  /**
   * Register a new passenger account.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `registerAsPassenger()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  registerAsPassenger$Response(params: RegisterAsPassenger$Params, context?: HttpContext): Observable<StrictHttpResponse<PassengerDto>> {
    return registerAsPassenger(this.http, this.rootUrl, params, context);
  }

  /**
   * Register a new passenger account.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `registerAsPassenger$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  registerAsPassenger(params: RegisterAsPassenger$Params, context?: HttpContext): Observable<PassengerDto> {
    return this.registerAsPassenger$Response(params, context).pipe(
      map((r: StrictHttpResponse<PassengerDto>): PassengerDto => r.body)
    );
  }

  /** Path part for operation `registerAsInspector()` */
  static readonly RegisterAsInspectorPath = '/auth/register/inspector';

  /**
   * Register a new inspector account.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `registerAsInspector()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  registerAsInspector$Response(params: RegisterAsInspector$Params, context?: HttpContext): Observable<StrictHttpResponse<InspectorDto>> {
    return registerAsInspector(this.http, this.rootUrl, params, context);
  }

  /**
   * Register a new inspector account.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `registerAsInspector$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  registerAsInspector(params: RegisterAsInspector$Params, context?: HttpContext): Observable<InspectorDto> {
    return this.registerAsInspector$Response(params, context).pipe(
      map((r: StrictHttpResponse<InspectorDto>): InspectorDto => r.body)
    );
  }

  /** Path part for operation `login()` */
  static readonly LoginPath = '/auth/login';

  /**
   * Log in to the system.
   *
   * Returns a JWT, which should be included in the Authorization header of all subsequent requests.<br/>Can be used by both passengers and inspectors.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `login()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  login$Response(params: Login$Params, context?: HttpContext): Observable<StrictHttpResponse<LoginRes>> {
    return login(this.http, this.rootUrl, params, context);
  }

  /**
   * Log in to the system.
   *
   * Returns a JWT, which should be included in the Authorization header of all subsequent requests.<br/>Can be used by both passengers and inspectors.
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `login$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  login(params: Login$Params, context?: HttpContext): Observable<LoginRes> {
    return this.login$Response(params, context).pipe(
      map((r: StrictHttpResponse<LoginRes>): LoginRes => r.body)
    );
  }

}
