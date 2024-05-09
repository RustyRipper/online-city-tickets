package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class TimeLimitedOffer extends TicketOffer {

    @Column(nullable = false, updatable = false)
    @NotNull(message = "Duration is required")
    private Duration durationInMinutes;
}
