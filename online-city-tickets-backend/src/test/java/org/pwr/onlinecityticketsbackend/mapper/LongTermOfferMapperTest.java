package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LongTermOfferMapperTest {
    private LongTermOfferMapper sut;

    LongTermOfferMapperTest() {
        this.sut = LongTermOfferMapper.INSTANCE;
    }

    @Test
    void shouldMapModelToDto() {
        // given
        LongTermOffer model = LongTermOffer.builder()
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
        Assertions.assertEquals(model.getKind(), result.getKind());
        Assertions.assertEquals(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue(),
                result.getPriceGrosze());
        Assertions.assertEquals("long-term", result.getScope());
    }

}
