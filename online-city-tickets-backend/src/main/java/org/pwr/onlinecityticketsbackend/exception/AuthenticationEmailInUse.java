package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class AuthenticationEmailInUse extends RestApiException {

    public AuthenticationEmailInUse() {
        super(HttpStatus.CONFLICT, "EMAIL_IN_USE");
    }
}
