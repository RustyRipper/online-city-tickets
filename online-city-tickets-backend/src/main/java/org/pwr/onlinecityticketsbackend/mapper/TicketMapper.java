package org.pwr.onlinecityticketsbackend.mapper;

import org.mapstruct.*;
import org.pwr.onlinecityticketsbackend.dto.ticket.TicketDto;
import org.pwr.onlinecityticketsbackend.model.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = TicketOfferMapper.class)
public interface TicketMapper {
    TicketDto toDto(Ticket ticket);
}
