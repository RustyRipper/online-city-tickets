package org.pwr.onlinecityticketsbackend.dto.creditCardInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {
    Long id;
    String label;
    String lastFourDigits;
    String holderName;
    String expirationDate;
}
