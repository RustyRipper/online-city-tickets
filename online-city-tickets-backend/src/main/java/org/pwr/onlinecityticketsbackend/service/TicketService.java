package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import java.time.Clock;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.pwr.onlinecityticketsbackend.dto.ticket.*;
import org.pwr.onlinecityticketsbackend.exception.*;
import org.pwr.onlinecityticketsbackend.mapper.TicketMapper;
import org.pwr.onlinecityticketsbackend.model.*;
import org.pwr.onlinecityticketsbackend.repository.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final AccountRepository accountRepository;
    private final TicketOfferRepository ticketOfferRepository;
    private final ValidationRepository validationRepository;
    private final VehicleRepository vehicleRepository;
    private final Clock clock;

    public List<TicketDto> listTickets(Account account) throws AuthenticationInvalidRequest {
        if (!(account instanceof Passenger passenger)) {
            throw new AuthenticationInvalidRequest();
        }

        return ticketRepository.findByPassengerId(passenger.getId()).stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Transactional
    public TicketDto purchaseTicket(PurchaseTicketReqDto purchaseTicketReqDto, Account account)
            throws TicketInsufficientFunds, TicketOfferNotFound, AuthenticationInvalidRequest {
        if (!(account instanceof Passenger passenger)) {
            throw new AuthenticationInvalidRequest();
        }

        var ticketOffer =
                ticketOfferRepository
                        .findById(purchaseTicketReqDto.getOfferId())
                        .orElseThrow(TicketOfferNotFound::new);

        if (passenger.getWalletBalancePln().compareTo(ticketOffer.getPricePln()) < 0) {
            throw new TicketInsufficientFunds();
        }

        passenger.setWalletBalancePln(
                passenger.getWalletBalancePln().subtract(ticketOffer.getPricePln()));
        accountRepository.save(passenger);

        var ticket =
                Ticket.builder()
                        .code(RandomStringUtils.randomAlphanumeric(10).toUpperCase())
                        .passenger(passenger)
                        .offer(ticketOffer)
                        .purchaseTime(clock.instant())
                        .build();
        ticket = ticketRepository.save(ticket);

        return ticketMapper.toDto(ticket);
    }

    public TicketDto getTicket(String code, Account account)
            throws TicketNotFound, AuthenticationInvalidRequest {
        if (!(account instanceof Passenger passenger)) {
            throw new AuthenticationInvalidRequest();
        }

        var ticket =
                ticketRepository
                        .findByCode(code)
                        .filter(t -> t.getPassenger().getId().equals(passenger.getId()))
                        .orElseThrow(TicketNotFound::new);

        return ticketMapper.toDto(ticket);
    }

    @Transactional
    public TicketDto validateTicket(String code, ValidateTicketReq request)
            throws TicketNotFound, TicketAlreadyValidated, VehicleNotFound {

        var ticket =
                ticketRepository
                        .findByCode(code)
                        .filter(t -> t.getOffer() instanceof SingleFareOffer)
                        .orElseThrow(TicketNotFound::new);

        if (ticket.getValidation() != null) {
            throw new TicketAlreadyValidated();
        }

        var vehicle =
                vehicleRepository
                        .findBySideNumber(request.getVehicleSideNumber())
                        .orElseThrow(VehicleNotFound::new);

        var validation = Validation.builder().time(clock.instant()).vehicle(vehicle).build();
        validation = validationRepository.save(validation);

        ticket.setValidation(validation);
        ticketRepository.save(ticket);

        return ticketMapper.toDto(ticket);
    }

    public InspectTicketRes inspectTicket(String code, InspectTicketReq request, Account account)
            throws VehicleNotFound, AuthenticationInvalidRequest {
        if (!(account instanceof Inspector)) {
            throw new AuthenticationInvalidRequest();
        }

        vehicleRepository
                .findBySideNumber(request.getVehicleSideNumber())
                .orElseThrow(VehicleNotFound::new);

        return ticketRepository
                .findByCode(code)
                .map(t -> mapTicketToReason(t, request))
                .orElse(Optional.of("ticket-not-found"))
                .map(InspectTicketRes::invalid)
                .orElse(InspectTicketRes.valid());
    }

    private Optional<String> mapTicketToReason(Ticket ticket, InspectTicketReq request) {
        var isTicketSingleFareOffer = ticket.getOffer() instanceof SingleFareOffer;

        if (isTicketSingleFareOffer && ticket.getValidation() == null) {
            return Optional.of("ticket-not-validated");
        }

        if (isTicketSingleFareOffer
                && !ticket.getValidation()
                        .getVehicle()
                        .getSideNumber()
                        .equals(request.getVehicleSideNumber())) {
            return Optional.of("ticket-not-valid-for-vehicle");
        }

        if (!ticket.getIsActive(clock.instant())) {
            return Optional.of("ticket-expired");
        }

        return Optional.empty();
    }
}
