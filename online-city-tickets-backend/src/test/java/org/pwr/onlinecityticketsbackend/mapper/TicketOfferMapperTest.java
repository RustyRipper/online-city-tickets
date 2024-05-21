package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.LongTermTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.SingleFareTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.TimeLimitedTicketOfferDto;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.pwr.onlinecityticketsbackend.model.TicketOffer;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;

public class TicketOfferMapperTest {
    private final TicketOfferMapper sut = Mappers.getMapper(TicketOfferMapper.class);

    @ParameterizedTest
    @MethodSource("shouldMapToDtoParameterProvider")
    void shouldMapToDto1(TicketOffer model, Class<BaseTicketOfferDto> dtoClass) {
        // when
        var result = sut.toDto(model);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isInstanceOf(dtoClass);
    }

    static Stream<Arguments> shouldMapToDtoParameterProvider() {
        return Stream.of(
                Arguments.of(
                        SingleFareOffer.builder()
                                .id(1L)
                                .pricePln(BigDecimal.ONE)
                                .kind(TicketKind.STANDARD)
                                .build(),
                        SingleFareTicketOfferDto.class),
                Arguments.of(
                        LongTermOffer.builder()
                                .id(1L)
                                .pricePln(BigDecimal.ONE)
                                .kind(TicketKind.STANDARD)
                                .build(),
                        LongTermTicketOfferDto.class),
                Arguments.of(
                        TimeLimitedOffer.builder()
                                .id(1L)
                                .pricePln(BigDecimal.ONE)
                                .durationInMinutes(Duration.ofMinutes(1))
                                .kind(TicketKind.STANDARD)
                                .build(),
                        TimeLimitedTicketOfferDto.class));
    }

    @Test
    void shouldMapLongTermOfferToLongTermTicketOfferDto() {
        // given
        var model =
                LongTermOffer.builder()
                        .id(1L)
                        .displayNameEn("TestEn")
                        .displayNamePl("TestPl")
                        .intervalInDays(1)
                        .kind(TicketKind.STANDARD)
                        .pricePln(BigDecimal.ONE)
                        .build();

        // when
        var result = sut.toLongTermTicketOfferDto(model);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(model.getId());
        Assertions.assertThat(result.getDisplayNameEn()).isEqualTo(model.getDisplayNameEn());
        Assertions.assertThat(result.getDisplayNamePl()).isEqualTo(model.getDisplayNamePl());
        Assertions.assertThat(result.getIntervalInDays()).isEqualTo(model.getIntervalInDays());
        Assertions.assertThat(result.getKind()).isEqualTo(model.getKind().toString().toLowerCase());
        Assertions.assertThat(result.getPriceGrosze())
                .isEqualTo(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue());
        Assertions.assertThat(result.getScope()).isEqualTo("long-term");
    }

    @Test
    void shouldMapTimeLimitedOfferToTimeLimitedTicketOfferDto() {
        // given
        var model =
                TimeLimitedOffer.builder()
                        .id(1L)
                        .displayNameEn("TestEn")
                        .displayNamePl("TestPl")
                        .kind(TicketKind.STANDARD)
                        .pricePln(BigDecimal.ONE)
                        .durationInMinutes(Duration.ofMinutes(1))
                        .build();

        // when
        var result = sut.toTimeLimitedTicketOfferDto(model);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(model.getId());
        Assertions.assertThat(result.getDisplayNameEn()).isEqualTo(model.getDisplayNameEn());
        Assertions.assertThat(result.getDisplayNamePl()).isEqualTo(model.getDisplayNamePl());
        Assertions.assertThat(result.getKind()).isEqualTo(model.getKind().toString().toLowerCase());
        Assertions.assertThat(result.getPriceGrosze())
                .isEqualTo(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue());
        Assertions.assertThat(result.getDurationMinutes())
                .isEqualTo(model.getDurationInMinutes().toMinutes());
        Assertions.assertThat(result.getScope()).isEqualTo("time-limited");
    }

    @Test
    void shouldMapSingleFareOfferToSingleFareTicketOfferDto() {
        // given
        var model =
                SingleFareOffer.builder()
                        .id(1L)
                        .displayNameEn("TestEn")
                        .displayNamePl("TestPl")
                        .kind(TicketKind.STANDARD)
                        .pricePln(BigDecimal.ONE)
                        .build();

        // when
        var result = sut.toSingleFareTicketOfferDto(model);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(model.getId());
        Assertions.assertThat(result.getDisplayNameEn()).isEqualTo(model.getDisplayNameEn());
        Assertions.assertThat(result.getDisplayNamePl()).isEqualTo(model.getDisplayNamePl());
        Assertions.assertThat(result.getKind()).isEqualTo(model.getKind().toString().toLowerCase());
        Assertions.assertThat(result.getPriceGrosze())
                .isEqualTo(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue());
        Assertions.assertThat(result.getScope()).isEqualTo("single-fare");
    }
}
