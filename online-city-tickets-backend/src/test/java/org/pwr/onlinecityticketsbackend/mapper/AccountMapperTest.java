package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.account.InspectorDto;
import org.pwr.onlinecityticketsbackend.dto.account.PassengerDto;
import org.pwr.onlinecityticketsbackend.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AccountMapperTest {
    private final AccountMapper sut = Mappers.getMapper(AccountMapper.class);

    @Test
    void shouldMapPassengerToAccountDto() {
        // given

        var model =
                Passenger.builder()
                        .id(1L)
                        .email("passenger@example.com")
                        .fullName("Passenger Name")
                        .phoneNumber("123456789")
                        .walletBalancePln(new BigDecimal("100.00"))
                        .password(new BCryptPasswordEncoder().encode("password"))
                        .role(Role.PASSENGER)
                        .build();

        // when
        var result = (PassengerDto) sut.toDto(model);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo(model.getEmail());
        Assertions.assertThat(result.getFullName()).isEqualTo(model.getFullName());
        Assertions.assertThat(result.getPhoneNumber()).isEqualTo(model.getPhoneNumber());
        Assertions.assertThat(result.getType()).isEqualTo(model.getRole().toString().toLowerCase());
        Assertions.assertThat(result.getWalletBalanceGrosze())
                .isEqualTo(
                        model.getWalletBalancePln().multiply(BigDecimal.valueOf(100)).intValue());
    }

    @Test
    void shouldMapInspectorToAccountDto() {
        // given
        var model =
                Inspector.builder()
                        .id(1L)
                        .email("inspector@example.com")
                        .fullName("Inspector Name")
                        .password(new BCryptPasswordEncoder().encode("password"))
                        .role(Role.INSPECTOR)
                        .build();

        // when
        var result = (InspectorDto) sut.toDto(model);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo(model.getEmail());
        Assertions.assertThat(result.getFullName()).isEqualTo(model.getFullName());
        Assertions.assertThat(result.getType()).isEqualTo(model.getRole().toString().toLowerCase());
    }
}
