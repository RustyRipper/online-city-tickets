package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class CardAlreadySaved extends RestApiException {
    public CardAlreadySaved() {
        super(HttpStatus.CONFLICT, "CARD_ALREADY_SAVED");
    }
}
