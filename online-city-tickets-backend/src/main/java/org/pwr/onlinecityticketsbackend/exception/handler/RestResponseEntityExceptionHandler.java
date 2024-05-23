package org.pwr.onlinecityticketsbackend.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RestApiException.class})
    public ResponseEntity<Object> handleAll(RestApiException exception, WebRequest request) {
        var body =
                ExceptionHandlerBody.builder()
                        .status(exception.getHttpStatus().value())
                        .description(exception.getDescription())
                        .build();

        return handleExceptionInternal(
                exception, body, new HttpHeaders(), exception.getHttpStatus(), request);
    }
}
