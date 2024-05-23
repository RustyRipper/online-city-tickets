package org.pwr.onlinecityticketsbackend.utils.generator;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import org.pwr.onlinecityticketsbackend.model.CreditCardInfo;
import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountGenerator {
    private static final AtomicLong counter = new AtomicLong();

    public Passenger generatePassenger() {
        Passenger passenger =
                Passenger.builder()
                        .email("passenger" + counter.incrementAndGet() + "@example.com")
                        .fullName("Passenger " + counter.get())
                        .phoneNumber("123456789")
                        .password(new BCryptPasswordEncoder().encode("password"))
                        .walletBalancePln(new BigDecimal("1.0"))
                        .role(Role.PASSENGER)
                        .build();
        passenger.setDefaultCreditCard(
                CreditCardInfo.builder()
                        .owner(passenger)
                        .cardNumber("1234567890123456")
                        .expirationDate("12/23")
                        .holderName("123")
                        .label("default")
                        .build());
        return passenger;
    }

    public Inspector generateInspector() {
        return Inspector.builder()
                .email("inspector" + counter.incrementAndGet() + "@example.com")
                .fullName("Inspector " + counter.get())
                .password(new BCryptPasswordEncoder().encode("password"))
                .role(Role.INSPECTOR)
                .build();
    }
}
