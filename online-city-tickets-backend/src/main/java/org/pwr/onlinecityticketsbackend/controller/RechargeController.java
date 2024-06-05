package org.pwr.onlinecityticketsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithBlikReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithNewCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithSavedCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.exception.UnauthorizedUser;
import org.pwr.onlinecityticketsbackend.service.RechargeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recharge")
@RequiredArgsConstructor
public class RechargeController {
    private final RechargeService rechargeService;

    @PostMapping("/new-credit-card")
    public ResponseEntity<RechargeDto> rechargeWithNewCreditCard(
            @RequestBody RechargeWithNewCreditCardReqDto dto) throws UnauthorizedUser {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(rechargeService.rechargeWithNewCreditCard(dto, account));
    }

    @PostMapping("/saved-credit-card")
    public ResponseEntity<RechargeDto> rechargeWithSavedCreditCard(
            @RequestBody RechargeWithSavedCreditCardReqDto dto) throws UnauthorizedUser {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(rechargeService.rechargeWithSavedCreditCard(dto, account));
    }

    @PostMapping("/blik")
    public ResponseEntity<RechargeDto> rechargeWithBlik(@RequestBody RechargeWithBlikReqDto dto)
            throws UnauthorizedUser {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(rechargeService.rechargeWithBlik(dto, account));
    }
}
