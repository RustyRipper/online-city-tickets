package org.pwr.onlinecityticketsbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public final class PassengerDto extends AccountDto {
    private double walletBalanceGrosze;
}
