package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class TicketNotFound extends RestApiException {
    public TicketNotFound() {
        super(HttpStatus.NOT_FOUND, "TICKET_NOT_FOUND");
    }
}
