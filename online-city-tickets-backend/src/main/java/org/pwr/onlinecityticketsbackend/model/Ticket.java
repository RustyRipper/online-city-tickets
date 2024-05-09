package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    @NotNull(message = "Side number is required")
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

    @JoinColumn(nullable = false, name = "validation_id")
    @NotNull(message = "Validation is required")
    @OneToOne
    private Validation validation;

    private boolean getIsValid(Instant now, String sideNumber) {
        //TODO: Implement
        return false;
    }

    private Instant getValidFromTime() {
        //TODO: Implement
        return null;
    }

    private Instant getValidUntilTime() {
        //TODO: Implement
        return null;
    }
}
