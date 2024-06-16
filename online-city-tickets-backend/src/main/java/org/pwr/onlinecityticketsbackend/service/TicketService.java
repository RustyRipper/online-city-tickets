package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import java.time.Clock;
import java.util.List;
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

    public List<TicketDto> listTickets(Account account)
            throws UnauthorizedUser, AuthenticationInvalidRequest {
        if (!(account instanceof Passenger passenger)) {
            throw new AuthenticationInvalidRequest();
        }

        return ticketRepository.findByPassengerId(passenger.getId()).stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Transactional
    public TicketDto purchaseTicket(PurchaseTicketReqDto purchaseTicketReqDto, Account account)
            throws TicketInsufficientFunds,
                    UnauthorizedUser,
                    TicketOfferNotFound,
                    AuthenticationInvalidRequest {
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
            throws UnauthorizedUser, TicketNotFound, AuthenticationInvalidRequest {
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
            throws VehicleNotFound, UnauthorizedUser, AuthenticationInvalidRequest {
        if (!(account instanceof Inspector)) {
            throw new AuthenticationInvalidRequest();
        }

        vehicleRepository
                .findBySideNumber(request.getVehicleSideNumber())
                .orElseThrow(VehicleNotFound::new);

        InspectTicketRes response = new InspectTicketRes();
        var ticket = ticketRepository.findByCode(code);
        if (ticket.isEmpty()) {
            response.setStatus("invalid");
            response.setReason("ticket-not-found");
            return response;
        }

        if (ticket.get().getIsActive(clock.instant(), request.getVehicleSideNumber())) {
            response.setStatus("valid");
            response.setReason("");
        } else {
            response.setStatus("invalid");
            if (!ticket.get().getIsActive(clock.instant())) {
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
