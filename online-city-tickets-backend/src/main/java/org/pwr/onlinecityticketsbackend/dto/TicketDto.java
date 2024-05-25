package org.pwr.onlinecityticketsbackend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TicketDto {
    private String code;
    private LocalDateTime purchaseTime;
    private BaseTicketOfferDto offer;
    private ValidationDto validation;
}
