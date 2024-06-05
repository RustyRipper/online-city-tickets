package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class InvalidPaymentData extends RestApiException {
    public InvalidPaymentData() {
        super(HttpStatus.BAD_REQUEST, "INVALID_PAYMENT_DATA");
    }
}
