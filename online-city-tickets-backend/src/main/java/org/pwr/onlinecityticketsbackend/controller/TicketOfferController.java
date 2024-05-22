package org.pwr.onlinecityticketsbackend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.exception.TicketOfferNotFound;
import org.pwr.onlinecityticketsbackend.service.TicketOfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class TicketOfferController {
    private final TicketOfferService ticketOfferService;

    @GetMapping
    public ResponseEntity<List<? extends BaseTicketOfferDto>> getOffers() {
        return ResponseEntity.ok(ticketOfferService.getOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<? extends BaseTicketOfferDto> getOfferById(@PathVariable long id)
            throws TicketOfferNotFound {
        return ResponseEntity.ok(ticketOfferService.getOfferById(id));
    }
}
