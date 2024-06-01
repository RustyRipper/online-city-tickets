package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class CardExpired extends RestApiException {
    public CardExpired() {
        super(HttpStatus.BAD_REQUEST, "CARD_EXPIRED");
    }
}
