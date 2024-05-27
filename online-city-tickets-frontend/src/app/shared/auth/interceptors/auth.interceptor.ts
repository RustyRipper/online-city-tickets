import type { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { MessageService } from "primeng/api";
import { tap } from "rxjs";

import { AuthService } from "~/shared/auth/services/auth.service";
import { I18nService } from "~/shared/i81n/i18n.service";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const messageService = inject(MessageService);
  const i18n = inject(I18nService);
  const { jwt } = inject(AuthService);

  if (jwt !== null) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${jwt}`,
      },
    });
  }
  return next(req).pipe(
    tap({
      error: ({ status, error }) => {
        if (status === 401 && error?.description === "JWT_EXPIRED") {
          messageService.add({
            severity: "error",
            summary: i18n.t("auth-interceptor.jwt-expired"),
          });
        } else if (status < 400 || status >= 500) {
          messageService.add({
            severity: "error",
            summary: i18n.t("auth-interceptor.server-error"),
          });
        }
      },
    }),
  );
};
