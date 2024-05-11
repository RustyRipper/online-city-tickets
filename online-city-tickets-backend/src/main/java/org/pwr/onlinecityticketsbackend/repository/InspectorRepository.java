package org.pwr.onlinecityticketsbackend.repository;

import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    Optional<Inspector> findByEmail(String email);
}
