package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import java.time.Duration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.TimeLimitedTicketOfferDto;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;

@Mapper
public interface TimeLimitedOfferMapper {
    TimeLimitedOfferMapper INSTANCE = Mappers.getMapper(TimeLimitedOfferMapper.class);

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    @Mapping(source = "durationInMinutes", target = "durationMinutes", qualifiedByName = "durationInMinutes")
    TimeLimitedTicketOfferDto toTimeLimitedTicketOfferDto(TimeLimitedOffer timeLimitedOffer);

    @Named("pricePlnToGrosze")
    default int pricePlnToGrosze(BigDecimal pricePln) {
        return pricePln.multiply(BigDecimal.valueOf(100)).intValue();
    }

    @Named("durationInMinutes")
    default int durationInMinutes(Duration duration) {
        return (int) duration.toMinutes();
    }
}
