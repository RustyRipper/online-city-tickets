package org.pwr.onlinecityticketsbackend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ValidationDto {
    private LocalDateTime time;
    private String vehicleSideNumber;
}
