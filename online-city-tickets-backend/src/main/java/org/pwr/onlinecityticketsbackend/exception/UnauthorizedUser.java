package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class UnauthorizedUser extends RestApiException {
    public UnauthorizedUser() {
        super(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
    }
}
