package org.pwr.onlinecityticketsbackend.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {
    private final AccountSeeder accountSeeder;
    private final TicketOfferSeeder ticketOfferSeeder;

    public void seedData() {
        accountSeeder.seedAccounts();
        ticketOfferSeeder.seedTicketOffers();
    }
}
