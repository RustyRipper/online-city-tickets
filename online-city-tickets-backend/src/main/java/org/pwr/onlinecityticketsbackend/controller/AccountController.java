package org.pwr.onlinecityticketsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.account.UpdateAccountReqDto;
import org.pwr.onlinecityticketsbackend.exception.AccountNotFound;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationInvalidRequest;
import org.pwr.onlinecityticketsbackend.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PreAuthorize("hasAnyRole('ADMIN', 'PASSENGER', 'INSPECTOR')")
    @GetMapping
    public ResponseEntity<?> getAccount() throws AccountNotFound {
        return ResponseEntity.ok(accountService.getCurrentAccountByEmail());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PASSENGER', 'INSPECTOR')")
    @PatchMapping
    public ResponseEntity<?> updateAccount(@RequestBody UpdateAccountReqDto updateAccountReqDto)
            throws AuthenticationInvalidRequest, AccountNotFound {
        return ResponseEntity.ok(accountService.updateAccount(updateAccountReqDto));
    }
}
