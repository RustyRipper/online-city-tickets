package org.pwr.onlinecityticketsbackend.repository;

import org.pwr.onlinecityticketsbackend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByCode(String code);

    List<Ticket> findByPassengerEmail(String email);
}
