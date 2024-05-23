package org.pwr.onlinecityticketsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.exception.AccountNotFound;
import org.pwr.onlinecityticketsbackend.mapper.AccountMapper;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.ErrorResponse;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.pwr.onlinecityticketsbackend.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountMapper accountMapper;
    private final AccountService accountService;

    @PreAuthorize("hasAnyRole('ADMIN', 'PASSENGER', 'INSPECTOR')")
    @GetMapping
    public ResponseEntity<?> getAccount() throws AccountNotFound {
        Account account = RequestContext.getAccountFromRequest();
        assert account != null;
        if (account.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Invalid account."), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(
                accountMapper.toDto(accountService.getAccountByEmail(account.getEmail())));
    }
}
