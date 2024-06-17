package org.pwr.onlinecityticketsbackend.seeder;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.pwr.onlinecityticketsbackend.model.*;
import org.pwr.onlinecityticketsbackend.repository.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketSeeder {

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final TicketOfferRepository ticketOfferRepository;
    private final ValidationRepository validationRepository;

    public void seedTickets() {
        Optional<Passenger> richGuy = passengerRepository.findByEmail("richGuy@tickets.pl");
        List<TicketOffer> ticketOffers = ticketOfferRepository.findAll();

        for (TicketOffer offer : ticketOffers) {
            Ticket ticket =
                    Ticket.builder()
                            .code(RandomStringUtils.randomAlphanumeric(10).toUpperCase())
                            .passenger(richGuy.get())
                            .offer(offer)
                            .build();

            ticketRepository.save(ticket);
        }
        for (TicketOffer offer : ticketOffers) {
            Ticket ticket =
                    Ticket.builder()
                            .code(RandomStringUtils.randomAlphanumeric(10).toUpperCase())
                            .passenger(richGuy.get())
                            .offer(offer)
                            .purchaseTime(Instant.now().minus(Duration.ofDays(100)))
                            .build();

            ticketRepository.save(ticket);
        }

        Ticket ticket =
                Ticket.builder()
                        .code(RandomStringUtils.randomAlphanumeric(10).toUpperCase())
                        .passenger(richGuy.get())
                        .offer(ticketOffers.getFirst())
                        .purchaseTime(Instant.now())
                        .validation(validationRepository.findAll().get(0))
                        .build();
        Ticket ticket2 =
                Ticket.builder()
                        .code(RandomStringUtils.randomAlphanumeric(10).toUpperCase())
                        .passenger(richGuy.get())
                        .offer(ticketOffers.getFirst())
                        .purchaseTime(Instant.now().minus(Duration.ofDays(1)))
                        .validation(validationRepository.findAll().get(1))
                        .build();

        ticketRepository.save(ticket);
        ticketRepository.save(ticket2);
    }
}
