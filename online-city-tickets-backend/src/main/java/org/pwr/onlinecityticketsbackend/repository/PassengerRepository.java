package org.pwr.onlinecityticketsbackend.repository;

import java.util.Optional;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByEmail(String email);
}
