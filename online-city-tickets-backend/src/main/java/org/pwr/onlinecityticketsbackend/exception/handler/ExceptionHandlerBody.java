package org.pwr.onlinecityticketsbackend.exception.handler;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExceptionHandlerBody {
    private final int status;
    private final String description;
}
