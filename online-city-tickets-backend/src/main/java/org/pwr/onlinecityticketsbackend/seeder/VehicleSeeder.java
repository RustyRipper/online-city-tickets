package org.pwr.onlinecityticketsbackend.seeder;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.model.Vehicle;
import org.pwr.onlinecityticketsbackend.repository.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleSeeder {

    private final VehicleRepository vehicleRepository;

    public void seedVehicles() {

        Vehicle ve = Vehicle.builder().sideNumber("WAW 12345").isActive(true).build();
        Vehicle ve2 = Vehicle.builder().sideNumber("WAW 54321").isActive(true).build();
        vehicleRepository.save(ve);
        vehicleRepository.save(ve2);
    }
}
