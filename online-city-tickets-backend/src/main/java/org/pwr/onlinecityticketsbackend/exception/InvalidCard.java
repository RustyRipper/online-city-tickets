package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class InvalidCard extends RestApiException {
    public InvalidCard() {
        super(HttpStatus.BAD_REQUEST, "INVALID_CARD");
    }
}
