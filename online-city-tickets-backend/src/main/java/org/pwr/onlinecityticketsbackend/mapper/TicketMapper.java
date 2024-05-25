package org.pwr.onlinecityticketsbackend.mapper;

import org.mapstruct.*;
import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.TicketDto;
import org.pwr.onlinecityticketsbackend.model.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = TicketOfferMapper.class)
public interface TicketMapper {

    @Mapping(source = "offer", target = "offer", qualifiedByName = "mapToBaseTicketOfferDto", ignore = true)
    @Mapping(source = "validation.time", target = "validation.time", qualifiedByName = "mapToDateTime")
    @Mapping(source = "purchaseTime", target = "purchaseTime", qualifiedByName = "mapToDateTime")
    TicketDto toDto(Ticket ticket);

    @Named("mapToBaseTicketOfferDto")
    default BaseTicketOfferDto mapToBaseTicketOfferDto(TicketOffer offer, @Context TicketOfferMapper ticketOfferMapper) {
        return ticketOfferMapper.toDto(offer);
    }

    @Named("mapToDateTime")
    default LocalDateTime mapToDateTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
