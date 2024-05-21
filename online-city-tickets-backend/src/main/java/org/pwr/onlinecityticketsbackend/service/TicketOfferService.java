package org.pwr.onlinecityticketsbackend.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.mapper.TicketOfferMapper;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketOfferService {
    private final TicketOfferRepository ticketOfferRepository;
    private final TicketOfferMapper ticketOfferMapper;

    public List<BaseTicketOfferDto> getOffers() {
        return ticketOfferRepository.findAll().stream().map(ticketOfferMapper::toDto).toList();
    }

    // NOTE: In this method we use existsById before findById due to the strange behavior of the
    // findById method,
    // which returns deleted entities, which should not be the case, this is probably a bug in
    // Hibernate,
    // as there is no information in the documentation that findById should return soft deleted
    // entities.
    // It is also possible that this bug only occurs in a test environment, which would require
    // further testing.
    public Optional<BaseTicketOfferDto> getOfferById(long id) {
        return ticketOfferRepository.existsById(id)
                ? ticketOfferRepository.findById(id).map(ticketOfferMapper::toDto)
                : Optional.empty();
    }
}
