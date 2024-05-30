package org.pwr.onlinecityticketsbackend.dto;

import java.time.Instant;
import lombok.Data;

@Data
public class ValidationDto {
    private Instant time;
    private String vehicleSideNumber;
}
