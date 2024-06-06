package org.pwr.onlinecityticketsbackend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.CreditCardDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.SaveCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.UpdateCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.exception.CardAlreadySaved;
import org.pwr.onlinecityticketsbackend.exception.CardExpired;
import org.pwr.onlinecityticketsbackend.exception.CardNotFound;
import org.pwr.onlinecityticketsbackend.exception.InvalidCard;
import org.pwr.onlinecityticketsbackend.exception.NotPassenger;
import org.pwr.onlinecityticketsbackend.exception.UnauthorizedUser;
import org.pwr.onlinecityticketsbackend.service.CreditCardInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credit-cards")
@RequiredArgsConstructor
public class CreditCardInfoController {
    private final CreditCardInfoService creditCardInfoService;

    @GetMapping
    public ResponseEntity<List<CreditCardDto>> getAllCreditCards()
            throws UnauthorizedUser, NotPassenger {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(creditCardInfoService.getAllCreditCardsForUser(account));
    }

    @PostMapping
    public ResponseEntity<CreditCardDto> addCreditCard(
            @RequestBody SaveCreditCardReqDto saveCreditCardReqDto)
            throws UnauthorizedUser, NotPassenger, CardExpired, InvalidCard, CardAlreadySaved {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.status(201)
                .body(creditCardInfoService.addCreditCardForUser(saveCreditCardReqDto, account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDto> getCreditCardById(@PathVariable Long id)
            throws UnauthorizedUser, NotPassenger, CardNotFound {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(creditCardInfoService.getCreditCardByIdForUser(id, account));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreditCardDto> updateCreditCardById(
            @PathVariable Long id, @RequestBody UpdateCreditCardReqDto updateCreditCardReqDto)
            throws UnauthorizedUser, NotPassenger, CardExpired, InvalidCard, CardNotFound {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(
                creditCardInfoService.updateCreditCardByIdForUser(
                        id, updateCreditCardReqDto, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCreditCardById(@PathVariable Long id)
            throws UnauthorizedUser, NotPassenger, CardNotFound {
        var account = RequestContext.getAccountFromRequest();
        creditCardInfoService.deleteCreditCardByIdForUser(id, account);
        return ResponseEntity.noContent().build();
    }
}
