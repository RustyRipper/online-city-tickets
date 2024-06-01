package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class NotPassenger extends RestApiException {
    public NotPassenger() {
        super(HttpStatus.UNAUTHORIZED, "USER_IS_NOT_PASSENGER");
    }
}
