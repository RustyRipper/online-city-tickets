package org.pwr.onlinecityticketsbackend.seeder;

import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.model.Validation;
import org.pwr.onlinecityticketsbackend.model.Vehicle;
import org.pwr.onlinecityticketsbackend.repository.ValidationRepository;
import org.pwr.onlinecityticketsbackend.repository.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationSeeder {

    private final ValidationRepository validationRepository;
    private final VehicleRepository vehicleRepository;

    public void seedValidations() {

        Vehicle ve = vehicleRepository.findBySideNumber("WAW 12345").get();
        Validation validation =
                Validation.builder()
                        .time(Instant.now().plus(Duration.ofSeconds(1)))
                        .vehicle(ve)
                        .build();
        Validation validation2 =
                Validation.builder()
                        .time(Instant.now().minus(Duration.ofDays(1)).plus(Duration.ofSeconds(1)))
                        .vehicle(ve)
                        .build();

        validationRepository.save(validation);
        validationRepository.save(validation2);
    }
}
