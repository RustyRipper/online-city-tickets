package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;

public class TicketOfferMapperTest {
    private final TicketOfferMapper sut;

    TicketOfferMapperTest() {
        this.sut = TicketOfferMapper.INSTANCE;
    }

    @Test
    void shouldMapLongTermOfferToLongTermTicketOfferDto() {
        // given
        var model = LongTermOffer.builder()
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(model.getId(), result.getId());
        Assertions.assertEquals(model.getDisplayNameEn(), result.getDisplayNameEn());
        Assertions.assertEquals(model.getDisplayNamePl(), result.getDisplayNamePl());
        Assertions.assertEquals(model.getIntervalInDays(), result.getIntervalInDays());
        Assertions.assertEquals(model.getKind().toString().toLowerCase(), result.getKind());
        Assertions.assertEquals(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue(),
                result.getPriceGrosze());
        Assertions.assertEquals("long-term", result.getScope());
    }

    @Test
    void shouldMapTimeLimitedOfferToTimeLimitedTicketOfferDto() {
        // given
        var model = TimeLimitedOffer.builder()
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(model.getId(), result.getId());
        Assertions.assertEquals(model.getDisplayNameEn(), result.getDisplayNameEn());
        Assertions.assertEquals(model.getDisplayNamePl(), result.getDisplayNamePl());
        Assertions.assertEquals(model.getKind().toString().toLowerCase(), result.getKind());
        Assertions.assertEquals(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue(),
                result.getPriceGrosze());
        Assertions.assertEquals(model.getDurationInMinutes().toMinutes(), result.getDurationMinutes());
        Assertions.assertEquals("time-limited", result.getScope());
    }

    @Test
    void shouldMapSingleFareOfferToSingleFareTicketOfferDto() {
        // given
        var model = SingleFareOffer.builder()
                .id(1L)
                .displayNameEn("TestEn")
                .displayNamePl("TestPl")
                .kind(TicketKind.STANDARD)
                .pricePln(BigDecimal.ONE)
                .build();

        // when
        var result = sut.toSingleFareTicketOfferDto(model);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(model.getId(), result.getId());
        Assertions.assertEquals(model.getDisplayNameEn(), result.getDisplayNameEn());
        Assertions.assertEquals(model.getDisplayNamePl(), result.getDisplayNamePl());
        Assertions.assertEquals(model.getKind().toString().toLowerCase(), result.getKind());
        Assertions.assertEquals(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue(),
                result.getPriceGrosze());
        Assertions.assertEquals("single-fare", result.getScope());
    }
}
