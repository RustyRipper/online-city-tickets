package org.pwr.onlinecityticketsbackend.auth;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestPhone extends RegisterRequest {
    private String phoneNumber;
}
