package org.pwr.onlinecityticketsbackend.seeder;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

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
