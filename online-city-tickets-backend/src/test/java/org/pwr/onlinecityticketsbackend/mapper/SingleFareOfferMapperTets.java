package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SingleFareOfferMapperTets {
    private SingleFareOfferMapper sut;

    SingleFareOfferMapperTets() {
        this.sut = SingleFareOfferMapper.INSTANCE;
    }

    @Test
    void shouldMapModelToDto() {
        // given
        SingleFareOffer model = SingleFareOffer.builder()
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
        Assertions.assertEquals(model.getKind(), result.getKind());
        Assertions.assertEquals(model.getPricePln().multiply(BigDecimal.valueOf(100)).intValue(),
                result.getPriceGrosze());
        Assertions.assertEquals("single-fare", result.getScope());
    }
}
