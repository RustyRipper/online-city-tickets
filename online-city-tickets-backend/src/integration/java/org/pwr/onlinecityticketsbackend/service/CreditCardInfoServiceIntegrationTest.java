package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.SaveCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.UpdateCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.exception.CardAlreadySaved;
import org.pwr.onlinecityticketsbackend.exception.CardExpired;
import org.pwr.onlinecityticketsbackend.exception.CardNotFound;
import org.pwr.onlinecityticketsbackend.exception.InvalidCard;
import org.pwr.onlinecityticketsbackend.exception.NotPassenger;
import org.pwr.onlinecityticketsbackend.mapper.CreditCardInfoMapper;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.AccountSetup;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.CreditCardInfoSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@Transactional
public class CreditCardInfoServiceIntegrationTest {
    @Autowired private AccountSetup accountSetup;
    @Autowired private CreditCardInfoSetup creditCardInfoSetup;
    @Autowired private CreditCardInfoMapper creditCardInfoMapper;
    @Autowired private CreditCardInfoRepository creditCardInfoRepository;
    @Autowired private CreditCardInfoService sut;

    @TestConfiguration
    public static class TestConfig {
        @Bean
        Clock clock() {
            return Clock.fixed(Instant.parse("2030-11-01T12:00:00Z"), ZoneId.systemDefault());
        }
    }

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

    @Test
    void addCreditCardForUserShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();

        // when
        ThrowingCallable result =
                () -> sut.addCreditCardForUser(new SaveCreditCardReqDto(), inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @ParameterizedTest
    @MethodSource("addCreditCardForUserShouldThrowInvalidCardWhenCreditCardIsInvalidProvider")
    void addCreditCardForUserShouldThrowInvalidCardWhenCreditCardIsInvalid(
            SaveCreditCardReqDto invalidCreditCard) {
        // given
        var passenger = accountSetup.setupPassenger();

        // when
        ThrowingCallable result = () -> sut.addCreditCardForUser(invalidCreditCard, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidCard.class);
    }

    static Stream<Arguments>
            addCreditCardForUserShouldThrowInvalidCardWhenCreditCardIsInvalidProvider() {
        return Stream.of(
                Arguments.of(new SaveCreditCardReqDto("label", null, "Jan Kowalski", "12/99")),
                Arguments.of(new SaveCreditCardReqDto("label", "2137420697776666", null, "12/99")),
                Arguments.of(
                        new SaveCreditCardReqDto(
                                "label", "2137420697776666", "Jan Kowalski", null)),
                Arguments.of(
                        new SaveCreditCardReqDto(
                                "label", "2137420697776666", "Jan Kowalski", "00/99")),
                Arguments.of(
                        new SaveCreditCardReqDto(
                                "label", "2137420697776666", "Jan Kowalski", "1/99")),
                Arguments.of(
                        new SaveCreditCardReqDto(
                                "label", "2137420697776666", "Jan Kowalski", "12/9")),
                Arguments.of(
                        new SaveCreditCardReqDto(
                                "label", "2137420697776660", "Jan Kowalski", "12/99")));
    }

    @Test
    void addCreditCardForUserShouldThrowCardExpiredWhenCreditCardIsExpired() {
        // given
        var passenger = accountSetup.setupPassenger();
        var expiredCreditCard =
                new SaveCreditCardReqDto("label", "2137420697776666", "Jan Kowalski", "10/30");

        // when
        ThrowingCallable result = () -> sut.addCreditCardForUser(expiredCreditCard, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardExpired.class);
    }

    @Test
    void addCreditCardForUserShouldThrowCardAlreadySavedWhenCreditCardAlreadySaved() {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo =
                creditCardInfoSetup.setupCreditCardInfo(passenger, "12/30", "2137420697776666");
        var creditCardAlreadySaved =
                new SaveCreditCardReqDto(
                        null,
                        creditCardInfo.getCardNumber(),
                        creditCardInfo.getHolderName(),
                        creditCardInfo.getExpirationDate());

        // when
        ThrowingCallable result = () -> sut.addCreditCardForUser(creditCardAlreadySaved, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardAlreadySaved.class);
    }

    @Test
    void addCreditCardForUserShouldAddCreditCardInfoWhenCreditCardIsValidAndNotExpiredAndNotSaved()
            throws NotPassenger, CardExpired, InvalidCard, CardAlreadySaved {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardToAdd =
                new SaveCreditCardReqDto("label", "2137420697776666", "Jan Kowalski", "12/35");

        // when
        var result = sut.addCreditCardForUser(creditCardToAdd, passenger);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("label", creditCardToAdd.getLabel())
                .hasFieldOrPropertyWithValue("lastFourDigits", "6666")
                .hasFieldOrPropertyWithValue("expirationDate", creditCardToAdd.getExpirationDate())
                .hasFieldOrPropertyWithValue("holderName", creditCardToAdd.getHolderName());

        Assertions.assertThat(creditCardInfoRepository.findById(result.getId())).isPresent();
    }

    @Test
    void updateCreditCardByIdForUserShouldThrowNotPassengerWhenAccountIsNotPassenger() {
        // given
        var inspector = accountSetup.setupInspector();

        // when
        ThrowingCallable result =
                () -> sut.updateCreditCardByIdForUser(1L, new UpdateCreditCardReqDto(), inspector);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(NotPassenger.class);
    }

    @ParameterizedTest
    @MethodSource(
            "updateCreditCardByIdForUserShouldThrowInvalidCardWhenCreditCardIsInvalidProvider")
    void updateCreditCardByIdForUserShouldThrowInvalidCardWhenCreditCardIsInvalid(
            UpdateCreditCardReqDto updateCreditCardReqDto) {
        // given
        var passenger = accountSetup.setupPassenger();

        // when
        ThrowingCallable result =
                () -> sut.updateCreditCardByIdForUser(1L, updateCreditCardReqDto, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(InvalidCard.class);
    }

    static Stream<Arguments>
            updateCreditCardByIdForUserShouldThrowInvalidCardWhenCreditCardIsInvalidProvider() {
        return Stream.of(
                Arguments.of(new UpdateCreditCardReqDto("Lorem ipsum", "00/99")),
                Arguments.of(new UpdateCreditCardReqDto("Lorem ipsum", "1/99")),
                Arguments.of(new UpdateCreditCardReqDto("Lorem ipsum", "12/9")));
    }

    @Test
    void updateCreditCardByIdForUserShouldThrowCardExpiredWhenCreditCardIsExpired() {
        // given
        var passenger = accountSetup.setupPassenger();
        var expiredCreditCard = new UpdateCreditCardReqDto("Lorem ipsum", "10/30");

        // when
        ThrowingCallable result =
                () -> sut.updateCreditCardByIdForUser(1L, expiredCreditCard, passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardExpired.class);
    }

    @Test
    void updateCreditCardByIdForUserShouldThrowCardNotFoundWhenCreditCardInfoNotFound() {
        // given
        var passenger = accountSetup.setupPassenger();

        // when
        ThrowingCallable result =
                () -> sut.updateCreditCardByIdForUser(1L, new UpdateCreditCardReqDto(), passenger);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardNotFound.class);
    }

    @Test
    void
            updateCreditCardByIdForUserShouldThrowCardNotFoundWhenTryingToUpdateCreditCardInfoOfAnotherPassenger() {
        // given
        var passenger1 = accountSetup.setupPassenger();
        var passenger2 = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger1);

        // when
        ThrowingCallable result =
                () ->
                        sut.updateCreditCardByIdForUser(
                                creditCardInfo.getId(), new UpdateCreditCardReqDto(), passenger2);

        // then
        Assertions.assertThatThrownBy(result).isInstanceOf(CardNotFound.class);
    }

    @Test
    void updateCreditCardByIdForUserShouldUpdateCreditCardInfoWhenCreditCardIsValidAndNotExpired()
            throws NotPassenger, CardExpired, InvalidCard, CardNotFound {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger);
        var updateCreditCardReqDto = new UpdateCreditCardReqDto("Lorem ipsum", "12/35");

        // when
        var result =
                sut.updateCreditCardByIdForUser(
                        creditCardInfo.getId(), updateCreditCardReqDto, passenger);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("label", updateCreditCardReqDto.getLabel())
                .hasFieldOrPropertyWithValue(
                        "expirationDate", updateCreditCardReqDto.getExpirationDate());

        Assertions.assertThat(creditCardInfoRepository.findById(result.getId()))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("label", updateCreditCardReqDto.getLabel())
                .hasFieldOrPropertyWithValue(
                        "expirationDate", updateCreditCardReqDto.getExpirationDate());
    }

    @Test
    void updateCreditCardByIdForUserShouldUpdateLabelWhenCreditCardIsValidAndNotExpired()
            throws NotPassenger, CardExpired, InvalidCard, CardNotFound {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger);
        var updateCreditCardReqDto = new UpdateCreditCardReqDto("Lorem ipsum", null);

        // when
        var result =
                sut.updateCreditCardByIdForUser(
                        creditCardInfo.getId(), updateCreditCardReqDto, passenger);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("label", updateCreditCardReqDto.getLabel())
                .hasFieldOrPropertyWithValue("expirationDate", creditCardInfo.getExpirationDate());

        Assertions.assertThat(creditCardInfoRepository.findById(result.getId()))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("label", updateCreditCardReqDto.getLabel())
                .hasFieldOrPropertyWithValue("expirationDate", creditCardInfo.getExpirationDate());
    }

    @Test
    void updateCreditCardByIdForUserShouldUpdateExpirationDateWhenCreditCardIsValidAndNotExpired()
            throws NotPassenger, CardExpired, InvalidCard, CardNotFound {
        // given
        var passenger = accountSetup.setupPassenger();
        var creditCardInfo = creditCardInfoSetup.setupCreditCardInfo(passenger);
        var updateCreditCardReqDto = new UpdateCreditCardReqDto(null, "12/35");

        // when
        var result =
                sut.updateCreditCardByIdForUser(
                        creditCardInfo.getId(), updateCreditCardReqDto, passenger);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("label", creditCardInfo.getLabel())
                .hasFieldOrPropertyWithValue(
                        "expirationDate", updateCreditCardReqDto.getExpirationDate());

        Assertions.assertThat(creditCardInfoRepository.findById(result.getId()))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("label", creditCardInfo.getLabel())
                .hasFieldOrPropertyWithValue(
                        "expirationDate", updateCreditCardReqDto.getExpirationDate());
    }
}
