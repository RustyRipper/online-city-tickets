package org.pwr.onlinecityticketsbackend.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.PurchaseTicketReqDto;
import org.pwr.onlinecityticketsbackend.dto.TicketDto;
import org.pwr.onlinecityticketsbackend.exception.*;
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

    public List<TicketDto> listTickets() throws AccountNotFound, AuthenticationInvalidRequest {
        Account account = RequestContext.getAccountFromRequest();
        if (account.getRole().equals(Role.INSPECTOR)) {
            throw new AuthenticationInvalidRequest();
        }
        Passenger passenger = (Passenger) account;

        return ticketRepository.findByPassengerEmail(passenger.getEmail()).stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    public TicketDto purchaseTicket(PurchaseTicketReqDto purchaseTicketReqDto)
            throws TicketInsufficientFunds,
                    AccountNotFound,
                    TicketOfferNotFound,
                    AuthenticationInvalidRequest {
        Account account = RequestContext.getAccountFromRequest();
        if (account.getRole().equals(Role.INSPECTOR)) {
            throw new AuthenticationInvalidRequest();
        }

        Optional<TicketOffer> ticketOffer =
                ticketOfferRepository.findById(purchaseTicketReqDto.getOfferId());
        if (ticketOffer.isEmpty()) {
            throw new TicketOfferNotFound();
        }
        Passenger passenger = (Passenger) account;

        if (passenger.getWalletBalancePln().compareTo(ticketOffer.get().getPricePln()) < 0) {
            throw new TicketInsufficientFunds();
        }

        passenger.setWalletBalancePln(
                passenger.getWalletBalancePln().subtract(ticketOffer.get().getPricePln()));
        accountRepository.save(passenger);

        return ticketMapper.toDto(
                ticketRepository.save(
                        Ticket.builder()
                                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                                .passenger(passenger)
                                .offer(ticketOffer.get())
                                .purchaseTime(Instant.now())
                                .build()));
    }

    public TicketDto getTicket(String code)
            throws AccountNotFound, TicketNotFound, AuthenticationInvalidRequest {
        Account account = RequestContext.getAccountFromRequest();
        if (account.getRole().equals(Role.INSPECTOR)) {
            throw new AuthenticationInvalidRequest();
        }

        Optional<Ticket> ticket = ticketRepository.findByCode(code);
        if (ticket.isEmpty()) {
            throw new TicketNotFound();
        }
        return ticketMapper.toDto(ticket.get());
    }
}
