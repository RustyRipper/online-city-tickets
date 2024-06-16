package org.pwr.onlinecityticketsbackend.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {
    private final AccountSeeder accountSeeder;
    private final TicketOfferSeeder ticketOfferSeeder;
    private final TicketSeeder ticketSeeder;
    private final VehicleSeeder vehicleSeeder;
    private final ValidationSeeder validationSeeder;

    public void seedData() {
        accountSeeder.seedAccounts();
        ticketOfferSeeder.seedTicketOffers();
        vehicleSeeder.seedVehicles();
        validationSeeder.seedValidations();
        ticketSeeder.seedTickets();
    }
}
