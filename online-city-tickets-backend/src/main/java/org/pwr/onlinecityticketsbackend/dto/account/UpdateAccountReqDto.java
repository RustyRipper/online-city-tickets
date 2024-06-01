package org.pwr.onlinecityticketsbackend.dto.account;

import lombok.Data;

@Data
public class UpdateAccountReqDto {
    private String fullName;
    private String newPassword;
    private String phoneNumber;
}
