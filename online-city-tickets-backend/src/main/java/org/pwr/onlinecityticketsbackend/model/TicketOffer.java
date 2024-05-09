package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class TicketOffer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "IsActive is required")
    @Builder.Default
    private boolean isActive = true;

    @Column(nullable = false)
    @NotNull(message = "Display name En is required")
    private String displayNameEn;

    @Column(nullable = false)
    @NotNull(message = "Display name Pl is required")
    private String displayNamePl;

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Ticket kind is required")
    private TicketKind kind;

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Price Pln is required")
    private BigDecimal pricePln;
}
