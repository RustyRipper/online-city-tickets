import { inject } from "@angular/core";
import { ResolveFn } from "@angular/router";

import type { AccountDto } from "~/generated/api/models";
import { AccountsApi } from "~/generated/api/services";

export const accountResolver: ResolveFn<AccountDto> = (_route, _state) =>
  inject(AccountsApi).getAccount();
