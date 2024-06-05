package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends Account {

    @Column(unique = true, length = 15)
    @Size(max = 15, message = "Phone number is too long")
    private String phoneNumber;

    @NotNull(message = "Wallet balance is required")
    @Min(value = 0, message = "Wallet balance must be greater than or equal to 0")
    @Builder.Default
    private BigDecimal walletBalancePln = BigDecimal.ZERO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_PASSENGER"));
    }

    public long addGroszeToWalletBalance(long grosze) {
        walletBalancePln = walletBalancePln.add(BigDecimal.valueOf(grosze, 2));
        return walletBalancePln.multiply(BigDecimal.valueOf(100)).longValue();
    }
}
