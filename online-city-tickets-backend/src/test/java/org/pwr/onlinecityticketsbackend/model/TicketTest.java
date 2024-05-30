package org.pwr.onlinecityticketsbackend.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class TicketTest {
    @Test
    public void getIsValid_nowIsWithinValidTime_returnsTrue() {
        // Given
        Ticket ticket = new Ticket();
        TimeLimitedOffer offer = new TimeLimitedOffer();
        offer.setDurationInMinutes(Duration.ofHours(2));
        ticket.setOffer(offer);

        ticket.setPurchaseTime(Instant.now().minus(Duration.ofHours(1)));

        // When
        boolean isValid = ticket.getIsValid(Instant.now());

        // Then
        assertTrue(isValid);
    }

    @Test
    public void getIsValid_nowIsOutsideValidTime_returnsFalse() {
        // Given
        Ticket ticket = new Ticket();
        TimeLimitedOffer offer = new TimeLimitedOffer();
        offer.setDurationInMinutes(Duration.ofHours(2));
        ticket.setOffer(offer);
        ticket.setPurchaseTime(Instant.now().plus(Duration.ofHours(1)));

        // When
        boolean isValid = ticket.getIsValid(Instant.now());

        // Then
        assertFalse(isValid);
    }

    @Test
    public void getIsValid_singleFareOfferWithValidSideNumber_returnsTrue() {
        // Given
        Ticket ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());
        ticket.setValidation(new Validation());
        ticket.getValidation().setVehicle(new Vehicle());
        ticket.getValidation().getVehicle().setSideNumber("123");
        ticket.getValidation().setTime(Instant.now().plus(Duration.ofMinutes(1)));

        // When
        boolean isValid = ticket.getIsValid(Instant.now().plus(Duration.ofMinutes(2)), "123");

        // Then
        assertTrue(isValid);
    }

    @Test
    public void getIsValid_singleFareOfferWithInvalidSideNumber_returnsFalse() {
        // Given
        Ticket ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());
        ticket.setValidation(new Validation());
        ticket.getValidation().setVehicle(new Vehicle());
        ticket.getValidation().getVehicle().setSideNumber("123");

        // When
        boolean isValid = ticket.getIsValid(Instant.now(), "456");

        // Then
        assertFalse(isValid);
    }

    @Test
    public void getValidFromTime_singleFareOfferWithValidation_returnsValidationTime() {
        // Given
        Ticket ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());
        ticket.setValidation(new Validation());
        Instant validationTime = Instant.now();
        ticket.getValidation().setTime(validationTime);

        // When
        Instant validFromTime = ticket.getValidFromTime();

        // Then
        assertEquals(validationTime, validFromTime);
    }

    @Test
    public void
            getValidUntilTime_singleFareOfferWithValidation_returnsValidationTimePlusFourHours() {
        // Given
        Ticket ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());
        ticket.setValidation(new Validation());
        Instant validationTime = Instant.now();
        ticket.getValidation().setTime(validationTime);

        // When
        Instant validUntilTime = ticket.getValidUntilTime();

        // Then
        assertEquals(validationTime.plus(Duration.ofHours(4)), validUntilTime);
    }
}
