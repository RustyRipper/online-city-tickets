package org.pwr.onlinecityticketsbackend.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class TicketDto {
    private String code;
    private Instant purchaseTime;
    private BaseTicketOfferDto offer;
    private ValidationDto validation;
}
