package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithBlikReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithNewCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithSavedCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.exception.CardExpired;
import org.pwr.onlinecityticketsbackend.exception.CardNotFound;
import org.pwr.onlinecityticketsbackend.exception.InvalidCard;
import org.pwr.onlinecityticketsbackend.exception.InvalidPaymentData;
import org.pwr.onlinecityticketsbackend.exception.NotPassenger;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.AccountSetup;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.CreditCardInfoSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@Transactional
public class RechargeServiceIntegrationTest {
    @Autowired private AccountSetup accountSetup;
    @Autowired private CreditCardInfoSetup creditCardInfoSetup;
    @Autowired private AccountRepository accountRepository;
    @Autowired private Clock clock;
    @Autowired private RechargeService sut;

    @TestConfiguration
    public static class TestConfig {
        @Bean
        Clock clock() {
            return Clock.fixed(Instant.parse("2030-11-01T12:00:00Z"), ZoneId.systemDefault());
        }
    }

    private long plnToGrosze(BigDecimal pln) {
        return pln.multiply(BigDecimal.valueOf(100)).longValue();
    }

    private String clockString() {
        return clock.instant().atZone(clock.getZone()).toString();
    }

    @Test
    public void rechargeWithNewCreditCardShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();
        var dto = new RechargeWithNewCreditCardReqDto();

        // when
        ThrowingCallable result = () -> sut.rechargeWithNewCreditCard(dto, inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @Test
    public void rechargeWithNewCreditCardShouldThrowInvalidPaymentDataWhenAmountIsInvalid() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto = RechargeWithNewCreditCardReqDto.builder().amountGrosze(-1L).build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithNewCreditCard(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidPaymentData.class);
    }

    @Test
    public void rechargeWithNewCreditCardShouldThrowInvalidPaymentDataWhenCvcIsInvalid() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto =
                RechargeWithNewCreditCardReqDto.builder()
                        .amountGrosze(1L)
                        .cvc("12x")
                        .number("2137420697776666")
                        .holderName("John Doe")
                        .expirationDate("12/30")
                        .build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithNewCreditCard(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidPaymentData.class);
    }

    @Test
    public void rechargeWithNewCreditCardShouldThrowInvalidPaymentDataWhenCreditCardIsInvalid() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto = RechargeWithNewCreditCardReqDto.builder().amountGrosze(1L).cvc("123").build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithNewCreditCard(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidCard.class);
    }

    @Test
    public void rechargeWithNewCreditCardShouldThrowInvalidPaymentDataWhenCreditCardIsExpired() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto =
                RechargeWithNewCreditCardReqDto.builder()
                        .amountGrosze(1L)
                        .cvc("123")
                        .number("2137420697776666")
                        .holderName("John Doe")
                        .expirationDate("10/30")
                        .build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithNewCreditCard(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardExpired.class);
    }

    @Test
    public void rechargeWithNewCreditCardShouldReturnRechargeDtoWhenRechargeIsSuccessful()
            throws NotPassenger, InvalidPaymentData, InvalidCard, CardExpired {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto =
                RechargeWithNewCreditCardReqDto.builder()
                        .amountGrosze(100L)
                        .cvc("123")
                        .number("2137420697776666")
                        .holderName("John Doe")
                        .expirationDate("12/30")
                        .build();
        var expectedWalletBalance =
                plnToGrosze(passenger.getWalletBalancePln()) + dto.getAmountGrosze();

        // when
        var result = sut.rechargeWithNewCreditCard(dto, passenger);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("time", clockString())
                .hasFieldOrPropertyWithValue("newWalletBalanceGrosze", expectedWalletBalance);

        var updatedPassenger = (Passenger) accountRepository.findById(passenger.getId()).get();
        var updatedWalletBalance = plnToGrosze(updatedPassenger.getWalletBalancePln());

        Assertions.assertThat(updatedWalletBalance).isEqualTo(expectedWalletBalance);
    }

    @Test
    public void rechargeWithSavedCreditCardShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();
        var dto = new RechargeWithSavedCreditCardReqDto();

        // when
        ThrowingCallable result = () -> sut.rechargeWithSavedCreditCard(dto, inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @Test
    public void rechargeWithSavedCreditCardShouldThrowInvalidPaymentDataWhenAmountIsInvalid() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto = RechargeWithSavedCreditCardReqDto.builder().amountGrosze(-1L).build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithSavedCreditCard(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidPaymentData.class);
    }

    @Test
    public void rechargeWithSavedCreditCardShouldThrowInvalidPaymentDataWhenCvcIsInvalid() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto = RechargeWithSavedCreditCardReqDto.builder().amountGrosze(1L).cvc("12x").build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithSavedCreditCard(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidPaymentData.class);
    }

    @Test
    public void rechargeWithSavedCreditCardShouldThrowCardNotFoundWhenCreditCardIsNotFound() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto =
                RechargeWithSavedCreditCardReqDto.builder()
                        .amountGrosze(1L)
                        .cvc("123")
                        .creditCardId(1L)
                        .build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithSavedCreditCard(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardNotFound.class);
    }

    @Test
    public void rechargeWithSavedCreditCardShouldReturnRechargeDtoWhenRechargeIsSuccessful()
            throws NotPassenger, InvalidPaymentData, CardNotFound {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger);
        var dto =
                RechargeWithSavedCreditCardReqDto.builder()
                        .amountGrosze(100L)
                        .cvc("123")
                        .creditCardId(creditCardInfo.getId())
                        .build();
        var expectedWalletBalance =
                plnToGrosze(passenger.getWalletBalancePln()) + dto.getAmountGrosze();

        // when
        var result = sut.rechargeWithSavedCreditCard(dto, passenger);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("time", clockString())
                .hasFieldOrPropertyWithValue("newWalletBalanceGrosze", expectedWalletBalance);

        var updatedPassenger = (Passenger) accountRepository.findById(passenger.getId()).get();
        var updatedWalletBalance = plnToGrosze(updatedPassenger.getWalletBalancePln());

        Assertions.assertThat(updatedWalletBalance).isEqualTo(expectedWalletBalance);
    }

    @Test
    public void rechargeWithBlikShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();
        var dto = new RechargeWithBlikReqDto();

        // when
        ThrowingCallable result = () -> sut.rechargeWithBlik(dto, inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @Test
    public void rechargeWithBlikShouldThrowInvalidPaymentDataWhenAmountIsInvalid() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto = RechargeWithBlikReqDto.builder().amountGrosze(-1L).build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithBlik(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidPaymentData.class);
    }

    @Test
    public void rechargeWithBlikShouldThrowInvalidPaymentDataWhenBlikCodeIsInvalid() {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto = RechargeWithBlikReqDto.builder().amountGrosze(1L).blikCode("12x").build();

        // when
        ThrowingCallable result = () -> sut.rechargeWithBlik(dto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidPaymentData.class);
    }

    @Test
    public void rechargeWithBlikShouldReturnRechargeDtoWhenRechargeIsSuccessful()
            throws NotPassenger, InvalidPaymentData {
        // given
        var passenger = accountSetup.setupPassenger();
        var dto = RechargeWithBlikReqDto.builder().amountGrosze(100L).blikCode("123456").build();
        var expectedWalletBalance =
                plnToGrosze(passenger.getWalletBalancePln()) + dto.getAmountGrosze();

        // when
        var result = sut.rechargeWithBlik(dto, passenger);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("time", clockString())
                .hasFieldOrPropertyWithValue("newWalletBalanceGrosze", expectedWalletBalance);

        var updatedPassenger = (Passenger) accountRepository.findById(passenger.getId()).get();
        var updatedWalletBalance = plnToGrosze(updatedPassenger.getWalletBalancePln());

        Assertions.assertThat(updatedWalletBalance).isEqualTo(expectedWalletBalance);
    }
}
