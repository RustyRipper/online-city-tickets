package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class LongTermOffer extends TicketOffer {

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Duration is required")
    private int intervalInDays;
}
