package org.pwr.onlinecityticketsbackend.exception;

import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class VehicleNotFound extends RestApiException {
    public VehicleNotFound() {
        super(HttpStatus.NOT_FOUND, "VEHICLE_NOT_FOUND");
    }
}
