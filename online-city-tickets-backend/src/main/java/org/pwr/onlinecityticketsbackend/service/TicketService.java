package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.ticket.PurchaseTicketReqDto;
import org.pwr.onlinecityticketsbackend.dto.ticket.TicketDto;
import org.pwr.onlinecityticketsbackend.exception.*;
import org.pwr.onlinecityticketsbackend.mapper.TicketMapper;
import org.pwr.onlinecityticketsbackend.model.*;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.pwr.onlinecityticketsbackend.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final AccountRepository accountRepository;
    private final TicketOfferRepository ticketOfferRepository;

    public List<TicketDto> listTickets() throws AccountNotFound, AuthenticationInvalidRequest {
        Account account = RequestContext.getAccountFromRequest();
        if (!(account instanceof Passenger passenger)) {
            throw new AuthenticationInvalidRequest();
        }

        return ticketRepository.findByPassengerId(passenger.getId()).stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Transactional
    public TicketDto purchaseTicket(PurchaseTicketReqDto purchaseTicketReqDto)
            throws TicketInsufficientFunds,
                    AccountNotFound,
                    TicketOfferNotFound,
                    AuthenticationInvalidRequest {
        Account account = RequestContext.getAccountFromRequest();
        if (!(account instanceof Passenger passenger)) {
            throw new AuthenticationInvalidRequest();
        }

        Optional<TicketOffer> ticketOffer =
                ticketOfferRepository.findById(purchaseTicketReqDto.getOfferId());
        if (ticketOffer.isEmpty()) {
            throw new TicketOfferNotFound();
        }

        if (passenger.getWalletBalancePln().compareTo(ticketOffer.get().getPricePln()) < 0) {
            throw new TicketInsufficientFunds();
        }

        passenger.setWalletBalancePln(
                passenger.getWalletBalancePln().subtract(ticketOffer.get().getPricePln()));
        accountRepository.save(passenger);

        return ticketMapper.toDto(
                ticketRepository.save(
                        Ticket.builder()
                                .code(RandomStringUtils.randomAlphanumeric(10).toUpperCase())
                                .passenger(passenger)
                                .offer(ticketOffer.get())
                                .purchaseTime(Instant.now())
                                .build()));
    }

    public TicketDto getTicket(String code)
            throws AccountNotFound, TicketNotFound, AuthenticationInvalidRequest {
        Account account = RequestContext.getAccountFromRequest();
        if (!(account instanceof Passenger passenger)) {
            throw new AuthenticationInvalidRequest();
        }

        Optional<Ticket> ticket = ticketRepository.findByCode(code);
        if (ticket.isEmpty() || !ticket.get().getPassenger().getId().equals(passenger.getId())) {
            throw new TicketNotFound();
        }
        return ticketMapper.toDto(ticket.get());
    }
}
