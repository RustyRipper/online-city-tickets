package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Passenger extends Account {

    @Column(unique = true)
    private String phoneNumber;

    @NotNull(message = "Wallet balance is required")
    private BigDecimal walletBalancePln = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "default_credit_card_id")
    private CreditCardInfo defaultCreditCard;
}
