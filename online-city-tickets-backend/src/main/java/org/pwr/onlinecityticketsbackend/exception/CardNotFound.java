package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class CardNotFound extends RestApiException {
    public CardNotFound() {
        super(HttpStatus.NOT_FOUND, "CARD_NOT_FOUND");
    }
}
