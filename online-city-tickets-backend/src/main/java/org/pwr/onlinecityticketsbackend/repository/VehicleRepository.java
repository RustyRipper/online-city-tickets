package org.pwr.onlinecityticketsbackend.repository;

import org.pwr.onlinecityticketsbackend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findBySideNumber(String vehicleSideNumber);
}
