package org.pwr.onlinecityticketsbackend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract sealed class AccountDto permits PassengerDto, InspectorDto {
    private String email;
    private String fullName;
    private String type;
}
