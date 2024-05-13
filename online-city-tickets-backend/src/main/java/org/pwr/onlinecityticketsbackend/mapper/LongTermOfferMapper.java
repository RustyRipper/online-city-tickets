package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.LongTermTicketOfferDto;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;

@Mapper
public interface LongTermOfferMapper {
    LongTermOfferMapper INSTANCE = Mappers.getMapper(LongTermOfferMapper.class);

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    LongTermTicketOfferDto toLongTermTicketOfferDto(LongTermOffer longTermOffer);

    @Named("pricePlnToGrosze")
    default int pricePlnToGrosze(BigDecimal pricePln) {
        return pricePln.multiply(BigDecimal.valueOf(100)).intValue();
    }
}
