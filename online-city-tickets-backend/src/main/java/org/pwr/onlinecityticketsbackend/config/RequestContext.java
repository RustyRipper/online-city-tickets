package org.pwr.onlinecityticketsbackend.config;

import org.pwr.onlinecityticketsbackend.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public class RequestContext {
    public static Account getAccountFromRequest() {
        Object account = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (account instanceof Account) {
            return (Account) account;
        }
        return null;
    }
}
