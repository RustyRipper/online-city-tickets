package org.pwr.onlinecityticketsbackend.service;

import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithBlikReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithNewCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithSavedCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.pwr.onlinecityticketsbackend.service.common.CreditCardValidators;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RechargeService {
    private final CreditCardInfoRepository creditCardInfoRepository;
    private final CreditCardValidators creditCardValidators;
    private final Clock clock;

    public RechargeDto rechargeWithNewCreditCard(
            RechargeWithNewCreditCardReqDto dto, Account account) {
        throw new UnsupportedOperationException();
    }

    public RechargeDto rechargeWithSavedCreditCard(
            RechargeWithSavedCreditCardReqDto dto, Account account) {
        throw new UnsupportedOperationException();
    }

    public RechargeDto rechargeWithBlik(RechargeWithBlikReqDto dto, Account account) {
        throw new UnsupportedOperationException();
    }
}
