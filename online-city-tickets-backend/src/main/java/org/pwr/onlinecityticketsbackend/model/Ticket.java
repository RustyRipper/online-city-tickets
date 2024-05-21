package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Side number is required") private String code;

    @Column(updatable = false, nullable = false)
    @NotNull(message = "Purchase time is required") @Builder.Default
    private Instant purchaseTime = Instant.now();

    @JoinColumn(updatable = false, nullable = false, name = "passenger_id")
    @NotNull(message = "Passenger is required") @ManyToOne
    private Passenger passenger;

    @JoinColumn(updatable = false, nullable = false, name = "offer_id")
    @NotNull(message = "Offer is required") @ManyToOne
    private TicketOffer offer;

    @JoinColumn(name = "validation_id")
    @OneToOne
    private Validation validation;

    @SuppressWarnings("unused")
    private boolean getIsValid(Instant now, String sideNumber) {
        // TODO: Implement and remove "unused" warning suppression
        return false;
    }

    @SuppressWarnings("unused")
    private Instant getValidFromTime() {
        // TODO: Implement and remove "unused" warning suppression
        return null;
    }

    @SuppressWarnings("unused")
    private Instant getValidUntilTime() {
        // TODO: Implement and remove "unused" warning suppression
        return null;
    }
}
