package org.pwr.onlinecityticketsbackend.dto.recharge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeWithNewCreditCardReqDto {
    long amountGrosze;
    String number;
    String holderName;
    String expirationDate;
    String cvc;
}
