package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import java.time.Duration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.LongTermTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.SingleFareTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.TimeLimitedTicketOfferDto;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.pwr.onlinecityticketsbackend.model.TicketOffer;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TicketOfferMapper {
    default BaseTicketOfferDto toDto(TicketOffer ticketOffer) {
        return switch (ticketOffer) {
            case SingleFareOffer offer -> toSingleFareTicketOfferDto(offer);
            case TimeLimitedOffer offer -> toTimeLimitedTicketOfferDto(offer);
            case LongTermOffer offer -> toLongTermTicketOfferDto(offer);
            default -> throw new IllegalStateException();
        };
    }

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    @Mapping(source = "kind", target = "kind", qualifiedByName = "kindToString")
    SingleFareTicketOfferDto toSingleFareTicketOfferDto(SingleFareOffer singleFareOffer);

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    @Mapping(source = "kind", target = "kind", qualifiedByName = "kindToString")
    LongTermTicketOfferDto toLongTermTicketOfferDto(LongTermOffer longTermOffer);

    @Mapping(source = "pricePln", target = "priceGrosze", qualifiedByName = "pricePlnToGrosze")
    @Mapping(source = "kind", target = "kind", qualifiedByName = "kindToString")
    @Mapping(
            source = "durationInMinutes",
            target = "durationMinutes",
            qualifiedByName = "durationInMinutes")
    TimeLimitedTicketOfferDto toTimeLimitedTicketOfferDto(TimeLimitedOffer timeLimitedOffer);

    @Named("pricePlnToGrosze")
    default int pricePlnToGrosze(BigDecimal pricePln) {
        return pricePln.multiply(BigDecimal.valueOf(100)).intValue();
    }

    @Named("durationInMinutes")
    default int durationInMinutes(Duration duration) {
        return (int) duration.toMinutes();
    }

    @Named("kindToString")
    default String kindToString(TicketKind kind) {
        return kind.toString().toLowerCase();
    }
}
