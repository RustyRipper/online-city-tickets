package org.pwr.onlinecityticketsbackend.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pwr.onlinecityticketsbackend.mapper.TicketOfferMapper;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketOfferServiceTest {
    @Mock
    private TicketOfferRepository ticketOfferRepository;

    @InjectMocks
    private TicketOfferService sut;

    @Autowired
    private TicketOfferService ticketOfferService;

    @Test
    void shouldGetAllSeededOffers() {
        // when
        var result = ticketOfferService.getOffers();

        // then
        Assertions.assertEquals(30, result.size());
        Assertions.assertEquals(2,
                result.stream().filter(offer -> offer.getScope().equals("single-fare")).count());
        Assertions.assertEquals(8,
                result.stream().filter(offer -> offer.getScope().equals("time-limited")).count());
        Assertions.assertEquals(20,
                result.stream().filter(offer -> offer.getScope().equals("long-term")).count());
        Assertions.assertEquals(15,
                result.stream().filter(offer -> offer.getKind().equals(TicketKind.STANDARD)).count());
        Assertions.assertEquals(15,
                result.stream().filter(offer -> offer.getKind().equals(TicketKind.REDUCED)).count());
    }

    @Test
    void shouldGetAllOffers() {
        // given
        var singleFareOffer = SingleFareOffer.builder().pricePln(BigDecimal.ONE).build();
        var longTermOffer = LongTermOffer.builder().pricePln(BigDecimal.ONE).build();
        var timeLimitedOffer = TimeLimitedOffer.builder().pricePln(BigDecimal.ONE)
                .durationInMinutes(Duration.ofMinutes(1)).build();

        // when
        when(ticketOfferRepository.findAll()).thenReturn(java.util.List.of(singleFareOffer, longTermOffer,
                timeLimitedOffer));
        var result = sut.getOffers();

        // then
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void shouldGetOfferById() {
        // given
        var singleFareOffer = SingleFareOffer.builder().id(1L).pricePln(BigDecimal.ONE).build();
        var longTermOffer = LongTermOffer.builder().id(2L).pricePln(BigDecimal.ONE).build();
        var timeLimitedOffer = TimeLimitedOffer.builder().id(3L).pricePln(BigDecimal.ONE)
                .durationInMinutes(Duration.ofMinutes(1)).build();

        // when
        when(ticketOfferRepository.findById(1L)).thenReturn(java.util.Optional.of(singleFareOffer));
        when(ticketOfferRepository.findById(2L)).thenReturn(java.util.Optional.of(longTermOffer));
        when(ticketOfferRepository.findById(3L)).thenReturn(java.util.Optional.of(timeLimitedOffer));
        var resultSingleFare = sut.getOfferById(1L);
        var resultLongTerm = sut.getOfferById(2L);
        var resultTimeLimited = sut.getOfferById(3L);

        // then
        Assertions.assertEquals(TicketOfferMapper.INSTANCE.toSingleFareTicketOfferDto(singleFareOffer),
                resultSingleFare.get());
        Assertions.assertEquals(TicketOfferMapper.INSTANCE.toLongTermTicketOfferDto(longTermOffer),
                resultLongTerm.get());
        Assertions.assertEquals(TicketOfferMapper.INSTANCE.toTimeLimitedTicketOfferDto(timeLimitedOffer),
                resultTimeLimited.get());
    }

    @Test
    void shouldNotGetOfferByIdWhenNotExists() {
        // when
        var result = sut.getOfferById(0L);

        // then
        Assertions.assertTrue(result.isEmpty());
    }
}