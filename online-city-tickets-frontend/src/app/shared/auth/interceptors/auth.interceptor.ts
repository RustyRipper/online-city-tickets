import type { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";

import { AuthService } from "../services/auth.service";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const { jwt } = inject(AuthService);
  if (jwt !== null) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${jwt}`,
      },
    });
  }
  return next(req);
};
