package org.pwr.onlinecityticketsbackend.utils.generator;

import java.math.BigDecimal;
import java.time.Duration;
import net.datafaker.Faker;
import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TicketKind;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;

public final class TicketOfferGenerator {
    private static Faker faker = new Faker();

    public static SingleFareOffer generateSingleFareOffer() {
        return SingleFareOffer.builder()
                .displayNameEn(faker.lorem().word())
                .displayNamePl(faker.lorem().word())
                .kind(faker.options().option(TicketKind.class))
                .pricePln(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000)))
                .build();
    }

    public static TimeLimitedOffer generateTimeLimitedOffer() {
        return TimeLimitedOffer.builder()
                .displayNameEn(faker.lorem().word())
                .displayNamePl(faker.lorem().word())
                .kind(faker.options().option(TicketKind.class))
                .pricePln(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000)))
                .durationInMinutes(Duration.ofMinutes(faker.number().numberBetween(1, 1440)))
                .build();
    }

    public static LongTermOffer generateLongTermOffer() {
        return LongTermOffer.builder()
                .displayNameEn(faker.lorem().word())
                .displayNamePl(faker.lorem().word())
                .kind(faker.options().option(TicketKind.class))
                .pricePln(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000)))
                .intervalInDays(faker.number().numberBetween(1, 365))
                .build();
    }
}
