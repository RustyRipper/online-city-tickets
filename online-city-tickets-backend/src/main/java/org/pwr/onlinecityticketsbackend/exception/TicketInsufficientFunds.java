package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class TicketInsufficientFunds extends RestApiException {
    public TicketInsufficientFunds() {
        super(HttpStatus.PAYMENT_REQUIRED, "INSUFFICIENTS_FUNDS");
    }
}
