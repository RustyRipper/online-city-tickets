package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class AccountNotFound extends RestApiException {
    public AccountNotFound() {
        super(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND");
    }
}
