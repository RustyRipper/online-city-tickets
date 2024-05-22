package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.TicketOfferNotFound;
import org.pwr.onlinecityticketsbackend.mapper.TicketOfferMapper;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.TicketOfferSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class TicketOfferServiceIntegrationTest {
    @Autowired private TicketOfferMapper ticketOfferMapper;

    @Autowired private TicketOfferSetup ticketOfferSetup;

    @Autowired private TicketOfferService sut;

    @Test
    public void shouldReturnNoOffersWhenNoOffersInDatabase() {
        // given

        // when
        var result = sut.getOffers();

        // then
        ListAssert.assertThatList(result).isEmpty();
    }

    @Test
    public void shouldReturnOffersWhenOffersInDatabase() {
        // given
        var singleFareOffer = ticketOfferSetup.setupSingleFareOffer();
        var timeLimitedOffer = ticketOfferSetup.setupTimeLimitedOffer();
        var longTermOffer = ticketOfferSetup.setupLongTermOffer();

        var expectedSingleFareTicketOfferDto =
                ticketOfferMapper.toSingleFareTicketOfferDto(singleFareOffer);
        var expectedTimeLimitedTicketOfferDto =
                ticketOfferMapper.toTimeLimitedTicketOfferDto(timeLimitedOffer);
        var expectedLongTermTicketOfferDto =
                ticketOfferMapper.toLongTermTicketOfferDto(longTermOffer);

        // when
        var result = sut.getOffers();

        // then
        ListAssert.assertThatList(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        expectedSingleFareTicketOfferDto,
                        expectedTimeLimitedTicketOfferDto,
                        expectedLongTermTicketOfferDto);
    }

    @Test
    public void shouldReturnOffersWhenOffersInDatabaseAndSomeAreInactive() {
        // given
        ticketOfferSetup.setupSingleFareOffer(false);
        ticketOfferSetup.setupTimeLimitedOffer(false);
        var longTermOffer = ticketOfferSetup.setupLongTermOffer();

        var expectedLongTermTicketOfferDto =
                ticketOfferMapper.toLongTermTicketOfferDto(longTermOffer);

        // when
        var result = sut.getOffers();

        // then
        ListAssert.assertThatList(result)
                .hasSize(1)
                .containsExactly(expectedLongTermTicketOfferDto);
    }

    @Test
    public void shouldNotGetOfferByIdWhenNoOfferInDatabase() {
        // given
        var id = 1L;

        // when
        ThrowingCallable resultCallable = () -> sut.getOfferById(id);

        // then
        Assertions.assertThatThrownBy(resultCallable).isInstanceOf(TicketOfferNotFound.class);
    }

    @Test
    public void shouldGetOfferByIdWhenOfferInDatabase() throws TicketOfferNotFound {
        // given
        var singleFareOffer = ticketOfferSetup.setupSingleFareOffer();
        var timeLimitedOffer = ticketOfferSetup.setupTimeLimitedOffer();
        var longTermOffer = ticketOfferSetup.setupLongTermOffer();

        var expectedSingleFareTicketOfferDto =
                ticketOfferMapper.toSingleFareTicketOfferDto(singleFareOffer);
        var expectedTimeLimitedTicketOfferDto =
                ticketOfferMapper.toTimeLimitedTicketOfferDto(timeLimitedOffer);
        var expectedLongTermTicketOfferDto =
                ticketOfferMapper.toLongTermTicketOfferDto(longTermOffer);

        // when
        var singleFareOfferResult = sut.getOfferById(singleFareOffer.getId());
        var timeLimitedOfferResult = sut.getOfferById(timeLimitedOffer.getId());
        var longTermOfferResult = sut.getOfferById(longTermOffer.getId());

        // then
        Assertions.assertThat(singleFareOfferResult).isNotNull();
        Assertions.assertThat(singleFareOfferResult).isEqualTo(expectedSingleFareTicketOfferDto);

        Assertions.assertThat(timeLimitedOfferResult).isNotNull();
        Assertions.assertThat(timeLimitedOfferResult).isEqualTo(expectedTimeLimitedTicketOfferDto);

        Assertions.assertThat(longTermOfferResult).isNotNull();
        Assertions.assertThat(longTermOfferResult).isEqualTo(expectedLongTermTicketOfferDto);
    }

    @Test
    public void shouldNotGetOfferByIdWhenOfferIsInactive() {
        // given
        var singleFareOffer = ticketOfferSetup.setupSingleFareOffer(false);

        // when
        ThrowingCallable resultCallable = () -> sut.getOfferById(singleFareOffer.getId());

        // then
        Assertions.assertThatThrownBy(resultCallable).isInstanceOf(TicketOfferNotFound.class);
    }
}
