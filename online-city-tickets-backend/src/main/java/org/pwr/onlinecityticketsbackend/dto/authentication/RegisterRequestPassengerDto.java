package org.pwr.onlinecityticketsbackend.dto.authentication;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestPassengerDto extends RegisterRequestDto {
    private String phoneNumber;
}
