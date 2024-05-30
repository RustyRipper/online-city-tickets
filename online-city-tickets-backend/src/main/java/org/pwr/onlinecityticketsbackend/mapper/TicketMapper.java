package org.pwr.onlinecityticketsbackend.mapper;

import java.time.Instant;
import org.mapstruct.*;
import org.pwr.onlinecityticketsbackend.dto.ticket.TicketDto;
import org.pwr.onlinecityticketsbackend.dto.ticket.TicketStatus;
import org.pwr.onlinecityticketsbackend.model.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TicketOfferMapper.class, ValidationMapper.class})
public interface TicketMapper {
    @Mapping(target = "ticketStatus", expression = "java(ticketStatus(ticket))")
    TicketDto toDto(Ticket ticket);

    @Named("ticketStatus")
    default String ticketStatus(Ticket ticket) {
        return String.valueOf(
                ticket.getIsActive(Instant.now()) ? TicketStatus.ACTIVE : TicketStatus.NOT_ACTIVE);
    }
}
