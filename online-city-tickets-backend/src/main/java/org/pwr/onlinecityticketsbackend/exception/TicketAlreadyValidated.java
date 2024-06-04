package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class TicketAlreadyValidated extends RestApiException {
    public TicketAlreadyValidated() {
        super(HttpStatus.CONFLICT, "ALREADY_VALIDATED");
    }
}
