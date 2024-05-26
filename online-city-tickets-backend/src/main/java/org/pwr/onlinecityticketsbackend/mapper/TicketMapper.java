package org.pwr.onlinecityticketsbackend.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.TicketDto;
import org.pwr.onlinecityticketsbackend.model.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = TicketOfferMapper.class)
public interface TicketMapper {

    TicketOfferMapper firstMapper = Mappers.getMapper(TicketOfferMapper.class);

    @Mapping(source = "offer", target = "offer", qualifiedByName = "mapToBaseTicketOfferDto")
    @Mapping(
            source = "validation.time",
            target = "validation.time",
            qualifiedByName = "mapToDateTime")
    @Mapping(source = "purchaseTime", target = "purchaseTime", qualifiedByName = "mapToDateTime")
    TicketDto toDto(Ticket ticket);

    @Named("mapToBaseTicketOfferDto")
    default BaseTicketOfferDto mapToBaseTicketOfferDto(TicketOffer offer) {
        return firstMapper.toDto(offer);
    }

    @Named("mapToDateTime")
    default LocalDateTime mapToDateTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
