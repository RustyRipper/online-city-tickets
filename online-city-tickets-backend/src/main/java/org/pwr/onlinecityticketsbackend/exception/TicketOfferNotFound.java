package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class TicketOfferNotFound extends RestApiException {
    public TicketOfferNotFound() {
        super(HttpStatus.NOT_FOUND, "Ticket offer not found.");
    }
}
