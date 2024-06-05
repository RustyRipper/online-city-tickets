package org.pwr.onlinecityticketsbackend.service;

import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithBlikReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithNewCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithSavedCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.exception.CardExpired;
import org.pwr.onlinecityticketsbackend.exception.CardNotFound;
import org.pwr.onlinecityticketsbackend.exception.InvalidCard;
import org.pwr.onlinecityticketsbackend.exception.InvalidPaymentData;
import org.pwr.onlinecityticketsbackend.exception.NotPassenger;
import org.pwr.onlinecityticketsbackend.mapper.RechargeMapper;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.pwr.onlinecityticketsbackend.service.common.CreditCardValidators;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RechargeService {
    private final CreditCardInfoRepository creditCardInfoRepository;
    private final AccountRepository accountRepository;
    private final CreditCardValidators creditCardValidators;
    private final RechargeMapper rechargeMapper;
    private final Clock clock;

    public RechargeDto rechargeWithNewCreditCard(
            RechargeWithNewCreditCardReqDto dto, Account account)
            throws NotPassenger, InvalidPaymentData, InvalidCard, CardExpired {
        if (!(account instanceof Passenger)) {
            throw new NotPassenger();
        }

        if (!isAmountValid(dto.getAmountGrosze())) {
            throw new InvalidPaymentData();
        }

        if (!CreditCardValidators.isCreditCardValid(rechargeMapper.rechargeToCreditCardDto(dto))) {
            throw new InvalidCard();
        }

        if (!CreditCardValidators.isValidLuhn(dto.getNumber())) {
            throw new InvalidCard();
        }

        if (creditCardValidators.isCreditCardExpired(dto.getExpirationDate())) {
            throw new CardExpired();
        }

        if (CreditCardValidators.isCvcValid(dto.getCvc())) {
            throw new InvalidPaymentData();
        }

        var passenger = (Passenger) account;
        var newWalletBalanceGrosze = passenger.addGroszeToWalletBalance(dto.getAmountGrosze());
        accountRepository.save(passenger);

        return RechargeDto.builder()
                .time(clock.instant().atZone(clock.getZone()).toString())
                .newWalletBalanceGrosze(newWalletBalanceGrosze)
                .build();
    }

    public RechargeDto rechargeWithSavedCreditCard(
            RechargeWithSavedCreditCardReqDto dto, Account account)
            throws NotPassenger, InvalidPaymentData, CardNotFound {
        if (!(account instanceof Passenger)) {
            throw new NotPassenger();
        }

        if (!isAmountValid(dto.getAmountGrosze())) {
            throw new InvalidPaymentData();
        }

        if (CreditCardValidators.isCvcValid(dto.getCvc())) {
            throw new InvalidPaymentData();
        }

        creditCardInfoRepository
                .findById(dto.getCreditCardId())
                .filter(c -> c.getOwner().equals(account))
                .orElseThrow(CardNotFound::new);

        var passenger = (Passenger) account;
        var newWalletBalanceGrosze = passenger.addGroszeToWalletBalance(dto.getAmountGrosze());
        accountRepository.save(passenger);

        return RechargeDto.builder()
                .time(clock.instant().atZone(clock.getZone()).toString())
                .newWalletBalanceGrosze(newWalletBalanceGrosze)
                .build();
    }

    public RechargeDto rechargeWithBlik(RechargeWithBlikReqDto dto, Account account)
            throws NotPassenger, InvalidPaymentData {
        if (!(account instanceof Passenger)) {
            throw new NotPassenger();
        }

        if (!isAmountValid(dto.getAmountGrosze())) {
            throw new InvalidPaymentData();
        }

        if (!isBlikCodeValid(dto.getBlikCode())) {
            throw new InvalidPaymentData();
        }

        var passenger = (Passenger) account;
        var newWalletBalanceGrosze = passenger.addGroszeToWalletBalance(dto.getAmountGrosze());
        accountRepository.save(passenger);

        return RechargeDto.builder()
                .time(clock.instant().atZone(clock.getZone()).toString())
                .newWalletBalanceGrosze(newWalletBalanceGrosze)
                .build();
    }

    private static boolean isAmountValid(long amount) {
        return amount > 0;
    }

    private static boolean isBlikCodeValid(String code) {
        return code.length() == 6 && code.chars().allMatch(Character::isDigit);
    }
}
