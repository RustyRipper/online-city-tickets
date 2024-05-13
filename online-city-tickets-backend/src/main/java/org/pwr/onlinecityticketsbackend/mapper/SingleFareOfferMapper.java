package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.SingleFareTicketOfferDto;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;

@Mapper
public interface SingleFareOfferMapper {
    SingleFareOfferMapper INSTANCE = Mappers.getMapper(SingleFareOfferMapper.class);

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    SingleFareTicketOfferDto toSingleFareTicketOfferDto(SingleFareOffer singleFareOffer);

    @Named("pricePlnToGrosze")
    default int pricePlnToGrosze(BigDecimal pricePln) {
        return pricePln.multiply(BigDecimal.valueOf(100)).intValue();
    }
}
