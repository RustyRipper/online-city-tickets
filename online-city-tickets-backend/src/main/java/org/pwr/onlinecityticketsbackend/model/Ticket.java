package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Duration;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true, length = 10)
    @Size(max = 10, message = "Code is too long")
    @NotNull(message = "Code is required")
    private String code;

    @Column(updatable = false, nullable = false)
    @NotNull(message = "Purchase time is required")
    @Builder.Default
    private Instant purchaseTime = Instant.now();

    @JoinColumn(updatable = false, nullable = false, name = "passenger_id")
    @NotNull(message = "Passenger is required")
    @ManyToOne
    private Passenger passenger;

    @JoinColumn(updatable = false, nullable = false, name = "offer_id")
    @NotNull(message = "Offer is required")
    @ManyToOne
    private TicketOffer offer;

    @JoinColumn(name = "validation_id")
    @OneToOne
    private Validation validation;

    public boolean getIsValid(Instant now, String sideNumber) {

        if (offer instanceof SingleFareOffer) {
            if (validation == null) {
                return false;
            }

            boolean isValidInVehicle = validation.getVehicle().getSideNumber().equals(sideNumber);
            if (!isValidInVehicle) {
                return false;
            }
        }
        return getIsValid(now);
    }

    public boolean getIsValid(Instant now) {
        Instant validFromTime = getValidFromTime();
        Instant validUntilTime = getValidUntilTime();

        if (validFromTime == null || validUntilTime == null) {
            return false;
        }

        return now.isAfter(validFromTime) && now.isBefore(validUntilTime);
    }

    public Instant getValidFromTime() {
        if (offer == null) {
            throw new IllegalStateException("Offer cannot be null");
        }

        if (offer instanceof SingleFareOffer) {
            return validation != null ? validation.getTime() : null;
        }

        return purchaseTime;
    }

    public Instant getValidUntilTime() {
        if (offer == null) {
            throw new IllegalStateException("Offer cannot be null");
        }

        if (offer instanceof SingleFareOffer) {
            return validation != null ? validation.getTime().plus(Duration.ofHours(4)) : null;
        } else if (offer instanceof TimeLimitedOffer) {
            return purchaseTime.plus(((TimeLimitedOffer) offer).getDurationInMinutes());
        } else if (offer instanceof LongTermOffer) {
            return purchaseTime.plus(Duration.ofDays(((LongTermOffer) offer).getIntervalInDays()));
        }

        throw new IllegalStateException("Offer type not supported");
    }
}
