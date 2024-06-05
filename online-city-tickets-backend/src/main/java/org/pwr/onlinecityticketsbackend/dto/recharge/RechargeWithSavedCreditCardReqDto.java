package org.pwr.onlinecityticketsbackend.dto.recharge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeWithSavedCreditCardReqDto {
    Long amountGrosze;
    Long creditCardId;
    String cvc;
}
