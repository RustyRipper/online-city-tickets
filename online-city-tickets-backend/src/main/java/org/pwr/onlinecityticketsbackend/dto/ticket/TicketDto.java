package org.pwr.onlinecityticketsbackend.dto.ticket;

import java.time.Instant;
import lombok.Data;
import org.pwr.onlinecityticketsbackend.dto.ticketOffer.BaseTicketOfferDto;

@Data
public class TicketDto {
    private String code;
    private Instant purchaseTime;
    private BaseTicketOfferDto offer;
    private ValidationDto validation;
}
