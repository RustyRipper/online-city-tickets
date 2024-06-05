package org.pwr.onlinecityticketsbackend.dto.recharge;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeDto {
    Instant time;
    Long newWalletBalanceGrosze;
}
