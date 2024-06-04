package org.pwr.onlinecityticketsbackend.repository;

import java.util.Optional;
import org.pwr.onlinecityticketsbackend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findBySideNumber(String vehicleSideNumber);
}
