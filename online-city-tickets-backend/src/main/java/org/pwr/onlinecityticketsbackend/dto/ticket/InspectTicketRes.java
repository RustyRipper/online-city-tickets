package org.pwr.onlinecityticketsbackend.dto.ticket;

import lombok.Data;

@Data
public class InspectTicketRes {
    private boolean isValid;
    private String message;
}
