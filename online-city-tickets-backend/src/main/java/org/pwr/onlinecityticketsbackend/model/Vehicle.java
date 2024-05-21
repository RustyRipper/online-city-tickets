package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "update vehicle set is_active=false where id=?")
@SQLRestriction("is_active IS NULL OR is_active=true")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "IsActive is required") @Builder.Default
    private boolean isActive = true;

    @Column(updatable = false, nullable = false, length = 10)
    @Size(max = 10, message = "Side number is too long")
    @NotNull(message = "Side number is required") private String sideNumber;
}
