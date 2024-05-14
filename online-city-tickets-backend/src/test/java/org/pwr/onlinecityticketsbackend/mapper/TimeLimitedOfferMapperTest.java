package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TimeLimitedOfferMapperTest {
    private TimeLimitedOfferMapper sut;

    TimeLimitedOfferMapperTest() {
        this.sut = TimeLimitedOfferMapper.INSTANCE;
    }

    @Test
    void shouldMapModelToDto() {
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
        Assertions.assertEquals(model.getKind(), result.getKind());
        Assertions.assertEquals(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue(),
                result.getPriceGrosze());
        Assertions.assertEquals(model.getDurationInMinutes().toMinutes(), result.getDurationMinutes());
        Assertions.assertEquals("time-limited", result.getScope());
    }
}
