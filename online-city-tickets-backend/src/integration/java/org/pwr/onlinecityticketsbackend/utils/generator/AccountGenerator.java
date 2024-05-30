package org.pwr.onlinecityticketsbackend.utils.generator;

import java.math.BigDecimal;
import net.datafaker.Faker;
import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class AccountGenerator {
    private static final Faker faker = new Faker();

    public static Passenger generatePassenger() {
        return Passenger.builder()
                .email(faker.internet().emailAddress())
                .fullName(faker.lorem().word())
                .phoneNumber(faker.numerify("89#######"))
                .password(new BCryptPasswordEncoder().encode(faker.lorem().word()))
                .walletBalancePln(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000)))
                .role(Role.PASSENGER)
                .build();
    }

    public static Inspector generateInspector() {
        return Inspector.builder()
                .email(faker.internet().emailAddress())
                .fullName(faker.lorem().word())
                .password(new BCryptPasswordEncoder().encode(faker.lorem().word()))
                .role(Role.INSPECTOR)
                .build();
    }
}
