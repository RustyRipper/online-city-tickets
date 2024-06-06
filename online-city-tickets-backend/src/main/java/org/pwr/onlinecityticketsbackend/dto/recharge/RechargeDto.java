package org.pwr.onlinecityticketsbackend.dto.recharge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeDto {
    String time;
    long newWalletBalanceGrosze;
}
