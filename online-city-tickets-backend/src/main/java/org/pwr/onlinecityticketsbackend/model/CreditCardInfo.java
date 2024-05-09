package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @Column(updatable = false, nullable = false)
    @NotNull(message = "Card number is required")
    private String cardNumber;

    @Column(updatable = false, nullable = false)
    @NotNull(message = "Expiration date is required")
    private String expirationDate;

    @Column(nullable = false)
    @NotNull(message = "Holder name is required")
    private String holderName;

    @ManyToOne
    @JoinColumn(updatable = false, nullable = false,name = "owner_id")
    @NotNull(message = "Holder name is required")
    private Passenger owner;
}
