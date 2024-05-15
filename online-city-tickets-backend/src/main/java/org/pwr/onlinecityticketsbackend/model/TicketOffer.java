package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "update ticket_offer set is_active=false where id=?")
@SQLRestriction("is_active IS NULL OR is_active=true")
public abstract class TicketOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "IsActive is required")
    @Builder.Default
    private boolean isActive = true;

    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "Display name En is too long")
    @NotNull(message = "Display name En is required")
    private String displayNameEn;

    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "Display name Pl is too long")
    @NotNull(message = "Display name Pl is required")
    private String displayNamePl;

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Ticket kind is required")
    private TicketKind kind;

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Price Pln is required")
    private BigDecimal pricePln;
}
