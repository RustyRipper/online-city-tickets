package org.pwr.onlinecityticketsbackend.repository;

import java.util.Optional;
import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    Optional<Inspector> findByEmail(String email);
}
