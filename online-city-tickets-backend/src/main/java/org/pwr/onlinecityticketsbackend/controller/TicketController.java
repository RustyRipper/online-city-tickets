package org.pwr.onlinecityticketsbackend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
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
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(ticketService.listTickets(account));
    }

    @PostMapping
    public ResponseEntity<TicketDto> purchaseTicket(
            @RequestBody PurchaseTicketReqDto purchaseTicketReq)
            throws TicketInsufficientFunds,
                    UnauthorizedUser,
                    TicketOfferNotFound,
                    AuthenticationInvalidRequest {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.status(201)
                .body(ticketService.purchaseTicket(purchaseTicketReq, account));
    }

    @GetMapping("/{code}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable String code)
            throws UnauthorizedUser, TicketNotFound, AuthenticationInvalidRequest {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(ticketService.getTicket(code, account));
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
            throws AuthenticationInvalidRequest, VehicleNotFound, UnauthorizedUser {
        var account = RequestContext.getAccountFromRequest();
        return ResponseEntity.ok(ticketService.inspectTicket(code, request, account));
    }
}
