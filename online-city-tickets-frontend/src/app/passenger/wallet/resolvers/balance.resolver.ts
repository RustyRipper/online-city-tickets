import { inject } from "@angular/core";
import type { ResolveFn } from "@angular/router";

import { WalletService } from "../services/wallet.service";

export const balanceResolver: ResolveFn<number> = (_route, _state) =>
  inject(WalletService).balanceGrosze$;
