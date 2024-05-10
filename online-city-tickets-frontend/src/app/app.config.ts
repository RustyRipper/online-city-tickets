import { ApplicationConfig, forwardRef } from "@angular/core";
import { provideRouter } from "@angular/router";
import { provideAnimations } from "@angular/platform-browser/animations";
import { HTTP_INTERCEPTORS, provideHttpClient } from "@angular/common/http";

import { routes } from "./app.routes";
import { AuthInterceptor } from "./auth/auth-interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(),
    {
      provide: HTTP_INTERCEPTORS,
      useExisting: forwardRef(() => AuthInterceptor),
      multi: true,
    },
    { provide: Storage, useValue: localStorage },
  ],
};
