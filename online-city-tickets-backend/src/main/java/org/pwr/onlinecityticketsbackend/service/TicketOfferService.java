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

    public List<BaseTicketOfferDto> getOffers() {
        var offers = ticketOfferRepository.findAll();
        var dtos = offers.stream().map(TicketOfferMapper.INSTANCE::toDto).toList();
        return dtos;
    }

    public Optional<BaseTicketOfferDto> getOfferById(long id) {
        var offer = ticketOfferRepository.findById(id);
        return offer.map(TicketOfferMapper.INSTANCE::toDto);
    }
}
