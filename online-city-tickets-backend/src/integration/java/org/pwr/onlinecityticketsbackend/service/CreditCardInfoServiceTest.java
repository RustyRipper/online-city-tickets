package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.CardNotFound;
import org.pwr.onlinecityticketsbackend.exception.NotPassenger;
import org.pwr.onlinecityticketsbackend.mapper.CreditCardInfoMapper;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.AccountSetup;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.CreditCardInfoSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class CreditCardInfoServiceTest {
    @Autowired private AccountSetup accountSetup;
    @Autowired private CreditCardInfoSetup creditCardInfoSetup;
    @Autowired private CreditCardInfoMapper creditCardInfoMapper;
    @Autowired private CreditCardInfoRepository creditCardInfoRepository;
    @Autowired private CreditCardInfoService sut;

    @Test
    public void getAllCreditCardsForUserShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();

        // when
        ThrowingCallable result = () -> sut.getAllCreditCardsForUser(inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @Test
    public void getAllCreditCardsForUserShouldReturnNoCreditCardInfoWhenNoCreditCardInfoInDatabase()
            throws NotPassenger {
        // given
        var passenger = accountSetup.setupPassenger();

        // when
        var result = sut.getAllCreditCardsForUser(passenger);

        // then
        ListAssert.assertThatList(result).isEmpty();
    }

    @Test
    public void getAllCreditCardsForUserShouldReturnCreditCardInfosWhenCreditCardInfosInDatabase()
            throws NotPassenger {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo1 = creditCardInfoSetup.setupCreditCardInfo(passenger);
        var creditCardInfo2 = creditCardInfoSetup.setupCreditCardInfo(passenger);

        var expectedCreditCardInfo1 = creditCardInfoMapper.toDto(creditCardInfo1);
        var expectedCreditCardInfo2 = creditCardInfoMapper.toDto(creditCardInfo2);

        // when
        var result = sut.getAllCreditCardsForUser(passenger);

        // then
        ListAssert.assertThatList(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(expectedCreditCardInfo1, expectedCreditCardInfo2);
    }

    @Test
    public void getCreditCardByIdForUserShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();

        // when
        ThrowingCallable result = () -> sut.getCreditCardByIdForUser(1L, inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @Test
    public void getCreditCardByIdForUserShouldThrowCardNotFoundWhenCreditCardInfoNotFound() {
        // given
        var passenger = accountSetup.setupPassenger();

        // when
        ThrowingCallable result = () -> sut.getCreditCardByIdForUser(1L, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardNotFound.class);
    }

    @Test
    public void
            getCreditCardByIdForUserShouldThrowCardNotFoundWhenTryingToGetCreditCardInfoOfAnotherPassenger() {
        // given
        var passenger1 = accountSetup.setupPassenger();
        var passenger2 = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger1);

        // when
        ThrowingCallable result =
                () -> sut.getCreditCardByIdForUser(creditCardInfo.getId(), passenger2);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardNotFound.class);
    }

    @Test
    public void getCreditCardByIdForUserShouldReturnCreditCardInfoWhenCreditCardInfoFound()
            throws NotPassenger, CardNotFound {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger);
        var expectedCreditCardInfo = creditCardInfoMapper.toDto(creditCardInfo);

        // when
        var result = sut.getCreditCardByIdForUser(creditCardInfo.getId(), passenger);

        // then
        Assertions.assertThat(result).isNotNull().isEqualTo(expectedCreditCardInfo);
    }

    @Test
    public void deleteCreditCardByIdForUserShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();

        // when
        ThrowingCallable result = () -> sut.deleteCreditCardByIdForUser(1L, inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @Test
    public void deleteCreditCardByIdForUserShouldThrowCardNotFoundWhenCreditCardInfoNotFound() {
        // given
        var passenger = accountSetup.setupPassenger();

        // when
        ThrowingCallable result = () -> sut.deleteCreditCardByIdForUser(1L, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardNotFound.class);
    }

    @Test
    public void
            deleteCreditCardByIdForUserShouldThrowCardNotFoundWhenTryingToDeleteCreditCardInfoOfAnotherPassenger() {
        // given
        var passenger1 = accountSetup.setupPassenger();
        var passenger2 = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger1);

        // when
        ThrowingCallable result =
                () -> sut.deleteCreditCardByIdForUser(creditCardInfo.getId(), passenger2);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardNotFound.class);
    }

    @Test
    public void deleteCreditCardByIdForUserShouldDeleteCreditCardInfoWhenCreditCardInfoFound()
            throws NotPassenger, CardNotFound {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger);

        // when
        sut.deleteCreditCardByIdForUser(creditCardInfo.getId(), passenger);

        // then
        Assertions.assertThat(creditCardInfoRepository.findById(creditCardInfo.getId())).isEmpty();
    }
}
