package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Passenger extends Account {

    @Column(unique = true, length = 15)
    @Size(max = 15, message = "Phone number is too long")
    private String phoneNumber;

    @NotNull(message = "Wallet balance is required")
    @Min(value = 0, message = "Wallet balance must be greater than or equal to 0")
    private BigDecimal walletBalancePln = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "default_credit_card_id")
    private CreditCardInfo defaultCreditCard;
}
