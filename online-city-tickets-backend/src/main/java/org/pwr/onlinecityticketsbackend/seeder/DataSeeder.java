package org.pwr.onlinecityticketsbackend.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {
    private final AccountSeeder accountSeeder;
    private final TicketOfferSeeder ticketOfferSeeder;
    private final TicketSeeder ticketSeeder;

    public void seedData() {
        accountSeeder.seedAccounts();
        ticketOfferSeeder.seedTicketOffers();
        ticketSeeder.seedTickets();
    }
}
