package org.pwr.onlinecityticketsbackend.dto.ticket;

import java.time.Instant;
import lombok.Data;

@Data
public class ValidationDto {
    private Instant time;
    private String vehicleSideNumber;
}
