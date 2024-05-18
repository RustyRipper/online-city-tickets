package org.pwr.onlinecityticketsbackend.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.LongTermTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.SingleFareTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.TimeLimitedTicketOfferDto;
import org.pwr.onlinecityticketsbackend.mapper.TicketOfferMapper;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketOffer;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;

@ExtendWith(MockitoExtension.class)
public class TicketOfferServiceTest {
    @Mock
    private TicketOfferRepository ticketOfferRepository;

    @Mock
    private TicketOfferMapper ticketOfferMapper;

    @InjectMocks
    private TicketOfferService sut;

    @Test
    void shouldGetAllOffers() {
        // given
        var singleFareOffer = new SingleFareOffer();
        var longTermOffer = new LongTermOffer();
        var timeLimitedOffer = new TimeLimitedOffer();

        var singleFareDto = new SingleFareTicketOfferDto();
        var longTermDto = new LongTermTicketOfferDto();
        var timeLimitedDto = new TimeLimitedTicketOfferDto();

        // when
        when(ticketOfferRepository.findAll())
                .thenReturn(List.of(singleFareOffer, longTermOffer, timeLimitedOffer));
        when(ticketOfferMapper.toDto(singleFareOffer)).thenReturn(singleFareDto);
        when(ticketOfferMapper.toDto(longTermOffer)).thenReturn(longTermDto);
        when(ticketOfferMapper.toDto(timeLimitedOffer)).thenReturn(timeLimitedDto);
        var result = sut.getOffers();

        // then
        Assertions.assertEquals(3, result.size());
        ListAssert.assertThatStream(result.stream()).containsExactly(singleFareDto, longTermDto, timeLimitedDto);
    }

    @Test
    void shouldGetNoOffersWhenEmpty() {
        // when
        when(ticketOfferRepository.findAll()).thenReturn(List.of());
        var result = sut.getOffers();

        // then
        Assertions.assertEquals(0, result.size());
    }

    @ParameterizedTest
    @MethodSource("shouldGetOfferByIdParameterProvider")
    void shouldGetOfferById(TicketOffer model, BaseTicketOfferDto dto) {
        // when
        when(ticketOfferRepository.findById(1L)).thenReturn(Optional.of(model));
        when(ticketOfferMapper.toDto(model)).thenReturn(dto);

        var result = sut.getOfferById(1L);

        // then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(dto, result.get());
    }

    static Stream<Arguments> shouldGetOfferByIdParameterProvider() {
        return Stream.of(
                Arguments.of(new SingleFareOffer(), new SingleFareTicketOfferDto()),
                Arguments.of(new LongTermOffer(), new LongTermTicketOfferDto()),
                Arguments.of(new TimeLimitedOffer(), new TimeLimitedTicketOfferDto()));
    }

    @Test
    void shouldNotGetOfferByIdWhenNotExists() {
        // when
        var result = sut.getOfferById(0L);

        // then
        Assertions.assertTrue(result.isEmpty());
    }

}
