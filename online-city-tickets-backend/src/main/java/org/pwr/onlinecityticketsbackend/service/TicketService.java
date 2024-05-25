package org.pwr.onlinecityticketsbackend.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.PurchaseTicketReqDto;
import org.pwr.onlinecityticketsbackend.dto.TicketDto;
import org.pwr.onlinecityticketsbackend.exception.TicketInsufficientFunds;
import org.pwr.onlinecityticketsbackend.mapper.TicketMapper;
import org.pwr.onlinecityticketsbackend.model.*;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.pwr.onlinecityticketsbackend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired private TicketMapper ticketMapper;
    @Autowired private TicketRepository ticketRepository;

    @Autowired private AccountRepository accountRepository;

    @Autowired private TicketOfferRepository ticketOfferRepository;

    public List<TicketDto> listTickets() {
        Account account = RequestContext.getAccountFromRequest();
        if (account.getRole().equals(Role.INSPECTOR)) {
            // throw new
        }
        Passenger passenger = (Passenger) account;

        return ticketRepository.findByPassengerEmail(passenger.getEmail()).stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    public TicketDto purchaseTicket(PurchaseTicketReqDto purchaseTicketReqDto)
            throws TicketInsufficientFunds {
        Account account = RequestContext.getAccountFromRequest();
        if (account.getRole().equals(Role.INSPECTOR)) {
            // throw new
        }

        Optional<TicketOffer> ticketOffer =
                ticketOfferRepository.findById(purchaseTicketReqDto.getOfferId());
        if (!ticketOffer.isPresent()) {
            // throw new
        }
        Passenger passenger = (Passenger) account;

        if (passenger.getWalletBalancePln().compareTo(ticketOffer.get().getPricePln()) < 0) {
            throw new TicketInsufficientFunds();
        }

        passenger.setWalletBalancePln(passenger.getWalletBalancePln().subtract(ticketOffer.get().getPricePln()));
        accountRepository.save(passenger);

        Ticket newTicket = new Ticket();
        newTicket.setPassenger(passenger);
        newTicket.setOffer(ticketOffer.get());
        newTicket.setPurchaseTime(Instant.from(LocalDateTime.now()));

        Ticket savedTicket = ticketRepository.save(newTicket);
        return ticketMapper.toDto(savedTicket);
    }

    public TicketDto getTicket(String code) {
        Account account = RequestContext.getAccountFromRequest();
        assert account != null;
        if (account.getRole().equals(Role.INSPECTOR)) {
            // throw new
        }

        Optional<Ticket> ticket = ticketRepository.findByCode(code);
        if (ticket.isEmpty()) {
            // throw new
        }
        return ticketMapper.toDto(ticket.get());
    }
}
