package org.pwr.onlinecityticketsbackend.config;

import org.pwr.onlinecityticketsbackend.exception.UnauthorizedUser;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public class RequestContext {
    public static Account getAccountFromRequest() throws UnauthorizedUser {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Account account) {
            return account;
        }
        throw new UnauthorizedUser();
    }
}
