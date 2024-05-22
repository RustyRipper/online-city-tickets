package org.pwr.onlinecityticketsbackend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String email;
    private String fullName;
    private String type;
    private String phoneNumber;
    private double walletBalanceGrosze;
    private long defaultCreditCardId;
}
