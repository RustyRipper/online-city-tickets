/* tslint:disable */
/* eslint-disable */
import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { AuthApi } from './services/auth-api';
import { AccountsApi } from './services/accounts-api';
import { CardsApi } from './services/cards-api';
import { RechargingApi } from './services/recharging-api';
import { OffersApi } from './services/offers-api';
import { TicketsApi } from './services/tickets-api';
import { ValidationApi } from './services/validation-api';
import { InspectionApi } from './services/inspection-api';

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    AuthApi,
    AccountsApi,
    CardsApi,
    RechargingApi,
    OffersApi,
    TicketsApi,
    ValidationApi,
    InspectionApi,
    ApiConfiguration
  ],
})
export class ApiModule {
  static forRoot(params: ApiConfigurationParams): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params
        }
      ]
    }
  }

  constructor( 
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
    }
    if (!http) {
      throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
      'See also https://github.com/angular/angular/issues/20575');
    }
  }
}
