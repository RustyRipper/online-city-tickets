package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class AuthenticationInvalidRequest extends RestApiException {
    public AuthenticationInvalidRequest() {
        super(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_BODY");
    }
}
