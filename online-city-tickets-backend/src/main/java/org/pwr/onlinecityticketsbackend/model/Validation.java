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
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false)
    @NotNull(message = "Card number is required")
    private Instant time;

    @ManyToOne
    @JoinColumn(updatable = false, nullable = false, name = "vehicle_id")
    @NotNull(message = "Vehicle is required")
    private Vehicle vehicle;
}
