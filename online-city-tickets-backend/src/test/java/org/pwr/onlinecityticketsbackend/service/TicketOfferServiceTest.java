package org.pwr.onlinecityticketsbackend.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.LongTermTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.SingleFareTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.TimeLimitedTicketOfferDto;
import org.pwr.onlinecityticketsbackend.exception.TicketOfferNotFound;
import org.pwr.onlinecityticketsbackend.mapper.TicketOfferMapper;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketOffer;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;

@ExtendWith(MockitoExtension.class)
public class TicketOfferServiceTest {
    @Mock private TicketOfferRepository ticketOfferRepository;

    @Mock private TicketOfferMapper ticketOfferMapper;

    @InjectMocks private TicketOfferService sut;

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
        ListAssert.assertThatList(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(singleFareDto, longTermDto, timeLimitedDto);
    }

    @Test
    void shouldGetNoOffersWhenEmpty() {
        // when
        when(ticketOfferRepository.findAll()).thenReturn(List.of());
        var result = sut.getOffers();

        // then
        ListAssert.assertThatList(result).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("shouldGetOfferByIdParameterProvider")
    void shouldGetOfferById(TicketOffer model, BaseTicketOfferDto dto) throws TicketOfferNotFound {
        // when
        when(ticketOfferRepository.existsById(1L)).thenReturn(true);
        when(ticketOfferRepository.findById(1L)).thenReturn(Optional.of(model));
        when(ticketOfferMapper.toDto(model)).thenReturn(dto);

        var result = sut.getOfferById(1L);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(dto);
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
        ThrowingCallable resultCallable = () -> sut.getOfferById(0L);

        // then
        Assertions.assertThatThrownBy(resultCallable).isInstanceOf(TicketOfferNotFound.class);
    }
}
