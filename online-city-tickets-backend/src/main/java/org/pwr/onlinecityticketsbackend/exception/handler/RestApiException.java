package org.pwr.onlinecityticketsbackend.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class RestApiException extends Exception {
    @Getter private final HttpStatus httpStatus;
    @Getter private final String description;

    public RestApiException(HttpStatus httpStatus, String description) {
        super();
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
