package org.pwr.onlinecityticketsbackend.service;

import java.util.List;
import java.util.Optional;

import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.mapper.TicketOfferMapper;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketOfferService {
    private final TicketOfferRepository ticketOfferRepository;
    private final TicketOfferMapper ticketOfferMapper;

    public List<BaseTicketOfferDto> getOffers() {
        return ticketOfferRepository.findAll().stream()
                .map(ticketOfferMapper::toDto)
                .toList();
    }

    public Optional<BaseTicketOfferDto> getOfferById(long id) {
        return ticketOfferRepository.existsById(id)
                ? ticketOfferRepository.findById(id).map(ticketOfferMapper::toDto)
                : Optional.empty();
    }
}
