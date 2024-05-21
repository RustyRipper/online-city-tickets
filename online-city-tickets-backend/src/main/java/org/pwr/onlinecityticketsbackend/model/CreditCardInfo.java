package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Column(length = 50)
    @Size(max = 50, message = "Label is too long")
    private String label;

    @Column(updatable = false, nullable = false, length = 16)
    @Size(max = 16, message = "Card number is too long")
    @NotNull(message = "Card number is required") private String cardNumber;

    @Column(nullable = false, length = 5)
    @Size(max = 5, message = "Expiration date is too long")
    @NotNull(message = "Expiration date is required") private String expirationDate;

    @Column(updatable = false, nullable = false, length = 25)
    @Size(max = 25, message = "Holder name is too long")
    @NotNull(message = "Holder name is required") private String holderName;

    @ManyToOne
    @JoinColumn(updatable = false, nullable = false, name = "owner_id")
    @NotNull(message = "Holder name is required") private Passenger owner;
}
