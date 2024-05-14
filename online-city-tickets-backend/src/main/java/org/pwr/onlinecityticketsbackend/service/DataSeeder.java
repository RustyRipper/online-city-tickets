package org.pwr.onlinecityticketsbackend.service;

import java.math.BigDecimal;
import java.time.Duration;

import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataSeeder {
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TicketOfferRepository ticketOfferRepository;

    public void seedData() {
        accountRepository.save(Account.builder()
                .email("admin@tickets.pl")
                .role(Role.valueOf("ADMIN"))
                .password(new BCryptPasswordEncoder().encode("12345678"))
                .fullName("Mr Admin")
                .build());

        accountService.createPassenger("passenger@tickets.pl", "Mr Passenger", "123456789", "12345678");
        accountService.createInspector("inspector@tickets.pl", "Mr Inspector", "12345678");

        seedTicketOffers(TicketKind.STANDARD);
        seedTicketOffers(TicketKind.REDUCED);
    }

    private void seedTicketOffers(TicketKind kind) {
        var priceMultiplier = (100.0 - kind.getDiscountPercent()) / 100.0;

        ticketOfferRepository.save(SingleFareOffer.builder()
                .displayNameEn("Single fare")
                .displayNamePl("Jednorazowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(4.60 * priceMultiplier))
                .build());

        ticketOfferRepository.save(TimeLimitedOffer.builder()
                .displayNameEn("15 minutes")
                .displayNamePl("15 minutowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(3.20 * priceMultiplier))
                .durationInMinutes(Duration.ofMinutes(15))
                .build());

        ticketOfferRepository.save(TimeLimitedOffer.builder()
                .displayNameEn("30 minutes")
                .displayNamePl("30 minutowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(4.00 * priceMultiplier))
                .durationInMinutes(Duration.ofMinutes(30))
                .build());

        ticketOfferRepository.save(TimeLimitedOffer.builder()
                .displayNameEn("60 minutes")
                .displayNamePl("60 minutowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(5.20 * priceMultiplier))
                .durationInMinutes(Duration.ofMinutes(60))
                .build());

        ticketOfferRepository.save(TimeLimitedOffer.builder()
                .displayNameEn("90 minutes")
                .displayNamePl("90 minutowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(7.00 * priceMultiplier))
                .durationInMinutes(Duration.ofMinutes(90))
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("24 hours")
                .displayNamePl("24 godzinny")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(15.00 * priceMultiplier))
                .intervalInDays(1)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("48 hours")
                .displayNamePl("48 godzinny")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(26.00 * priceMultiplier))
                .intervalInDays(2)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("72 hours")
                .displayNamePl("72 godzinny")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(32.00 * priceMultiplier))
                .intervalInDays(3)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("7 days")
                .displayNamePl("7 dniowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(38.0 * priceMultiplier))
                .intervalInDays(7)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("30 days")
                .displayNamePl("30 dniowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(110.0 * priceMultiplier))
                .intervalInDays(30)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("60 days")
                .displayNamePl("60 dniowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(208.0 * priceMultiplier))
                .intervalInDays(60)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("90 days")
                .displayNamePl("90 dniowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(302.0 * priceMultiplier))
                .intervalInDays(90)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("120 days")
                .displayNamePl("120 dniowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(400.0 * priceMultiplier))
                .intervalInDays(120)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("180 days")
                .displayNamePl("180 dniowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(560.0 * priceMultiplier))
                .intervalInDays(180)
                .build());

        ticketOfferRepository.save(LongTermOffer.builder()
                .displayNameEn("365 days")
                .displayNamePl("365 dniowy")
                .kind(kind)
                .pricePln(BigDecimal.valueOf(1050.0 * priceMultiplier))
                .intervalInDays(365)
                .build());
    }
}
