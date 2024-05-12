package org.pwr.onlinecityticketsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PreAuthorize("hasAnyRole('ADMIN', 'PASSENGER', 'INSPECTOR')")
    @GetMapping
    // TODO: Change return type to ResponseEntity<AccountDto>, hide password
    public ResponseEntity<Account> getAccount() {
        Account account = RequestContext.getAccountFromRequest();
        assert account != null;
        return ResponseEntity.ok(accountService.getAccountByEmail(account.getEmail()));
    }
}
