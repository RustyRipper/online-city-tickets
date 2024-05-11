package org.pwr.onlinecityticketsbackend.auth;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestPassenger extends RegisterRequest {
    private String phoneNumber;
}
