package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.ticket.*;
import org.pwr.onlinecityticketsbackend.dto.ticket.PurchaseTicketReqDto;
import org.pwr.onlinecityticketsbackend.dto.ticket.TicketDto;
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

    public List<TicketDto> listTickets() throws UnauthorizedUser, AuthenticationInvalidRequest {
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
                    UnauthorizedUser,
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
            throws UnauthorizedUser, TicketNotFound, AuthenticationInvalidRequest {
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

    @Transactional
    public TicketDto validateTicket(String code, ValidateTicketReq request)
            throws TicketNotFound, TicketAlreadyValidated, VehicleNotFound {

        Optional<Ticket> ticket = ticketRepository.findByCode(code);
        if (ticket.isEmpty() || !(ticket.get().getOffer() instanceof SingleFareOffer)) {
            throw new TicketNotFound();
        }

        if (ticket.get().getValidation() != null) {
            throw new TicketAlreadyValidated();
        }

        Optional<Vehicle> vehicle =
                vehicleRepository.findBySideNumber(request.getVehicleSideNumber());

        if (vehicle.isEmpty()) {
            throw new VehicleNotFound();
        }

        Validation validation = new Validation();
        validation.setTime(Instant.now());
        validation.setVehicle(vehicle.get());

        ticket.get().setValidation(validationRepository.save(validation));
        ticketRepository.save(ticket.get());

        return ticketMapper.toDto(ticket.get());
    }

    public InspectTicketRes inspectTicket(String code, InspectTicketReq request)
            throws TicketNotFound, VehicleNotFound, UnauthorizedUser, AuthenticationInvalidRequest {
        Account account = RequestContext.getAccountFromRequest();
        if (!(account instanceof Inspector)) {
            throw new AuthenticationInvalidRequest();
        }
        InspectTicketRes response = new InspectTicketRes();

        Optional<Ticket> ticket = ticketRepository.findByCode(code);
        if (ticket.isEmpty()) {
            response.setStatus("invalid");
            response.setReason("ticket-not-found");
            return response;
        }

        Optional<Vehicle> vehicle =
                vehicleRepository.findBySideNumber(request.getVehicleSideNumber());
        if (vehicle.isEmpty()) {
            throw new VehicleNotFound();
        }

        if (ticket.get().getIsActive(Instant.now(), request.getVehicleSideNumber())) {
            response.setStatus("valid");
            response.setReason("");
        } else {
            response.setStatus("invalid");
            if (!ticket.get().getIsActive(Instant.now())) {
                response.setReason("ticket-expired");
            } else if (ticket.get().getOffer() instanceof SingleFareOffer) {
                if (ticket.get().getValidation() == null) {
                    response.setReason("ticket-not-validated");
                } else if (!ticket.get()
                        .getValidation()
                        .getVehicle()
                        .getSideNumber()
                        .equals(request.getVehicleSideNumber())) {
                    response.setReason("ticket-not-valid-for-vehicle");
                }
            }
        }
        return response;
    }
}
