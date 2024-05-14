package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import java.time.Duration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.LongTermTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.SingleFareTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.TimeLimitedTicketOfferDto;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketOffer;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;

@Mapper
public interface TicketOfferMapper {
    TicketOfferMapper INSTANCE = Mappers.getMapper(TicketOfferMapper.class);

    default BaseTicketOfferDto toDto(TicketOffer ticketOffer) {
        if (ticketOffer instanceof SingleFareOffer) {
            return toSingleFareTicketOfferDto((SingleFareOffer) ticketOffer);
        } else if (ticketOffer instanceof TimeLimitedOffer) {
            return toTimeLimitedTicketOfferDto((TimeLimitedOffer) ticketOffer);
        } else if (ticketOffer instanceof LongTermOffer) {
            return toLongTermTicketOfferDto((LongTermOffer) ticketOffer);
        } else {
            return null;
        }
    }

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    SingleFareTicketOfferDto toSingleFareTicketOfferDto(SingleFareOffer singleFareOffer);

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    LongTermTicketOfferDto toLongTermTicketOfferDto(LongTermOffer longTermOffer);

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
