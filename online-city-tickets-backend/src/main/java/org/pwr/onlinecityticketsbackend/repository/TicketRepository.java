package org.pwr.onlinecityticketsbackend.repository;

import java.util.List;
import java.util.Optional;
import org.pwr.onlinecityticketsbackend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByCode(String code);

    List<Ticket> findByPassengerId(Long id);
}
