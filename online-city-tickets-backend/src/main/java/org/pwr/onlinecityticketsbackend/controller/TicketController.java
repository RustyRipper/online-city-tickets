package org.pwr.onlinecityticketsbackend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.ticket.PurchaseTicketReqDto;
import org.pwr.onlinecityticketsbackend.dto.ticket.TicketDto;
import org.pwr.onlinecityticketsbackend.dto.ticket.*;
import org.pwr.onlinecityticketsbackend.exception.*;
import org.pwr.onlinecityticketsbackend.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDto>> listTickets()
            throws UnauthorizedUser, AuthenticationInvalidRequest {
        return ResponseEntity.ok(ticketService.listTickets());
    }

    @PostMapping
    public ResponseEntity<TicketDto> purchaseTicket(
            @RequestBody PurchaseTicketReqDto purchaseTicketReq)
            throws TicketInsufficientFunds,
                    UnauthorizedUser,
                    TicketOfferNotFound,
                    AuthenticationInvalidRequest {
        return ResponseEntity.status(201).body(ticketService.purchaseTicket(purchaseTicketReq));
    }

    @GetMapping("/{code}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable String code)
            throws UnauthorizedUser, TicketNotFound, AuthenticationInvalidRequest {
        return ResponseEntity.ok(ticketService.getTicket(code));
    }

    @PostMapping("/{code}/validate")
    public ResponseEntity<TicketDto> validateTicket(
            @PathVariable String code, @RequestBody ValidateTicketReq request)
            throws TicketNotFound, VehicleNotFound, TicketAlreadyValidated {
        return ResponseEntity.ok(ticketService.validateTicket(code, request));
    }

    @PostMapping("/{code}/inspect")
    public ResponseEntity<InspectTicketRes> inspectTicket(
            @PathVariable String code, @RequestBody InspectTicketReq request)
            throws AuthenticationInvalidRequest, VehicleNotFound, TicketNotFound, UnauthorizedUser {
        return ResponseEntity.ok(ticketService.inspectTicket(code, request));
    }
}
