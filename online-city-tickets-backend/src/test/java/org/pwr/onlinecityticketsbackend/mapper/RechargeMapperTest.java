package org.pwr.onlinecityticketsbackend.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.SaveCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.recharge.RechargeWithNewCreditCardReqDto;

public class RechargeMapperTest {
    private final RechargeMapper sut = Mappers.getMapper(RechargeMapper.class);

    @Test
    public void
            rechargeToCreditCardDtoShouldMapRechargeWithNewCreditCardReqDtoToSaveCreditCardReqDto() {
        // given
        var dto =
                RechargeWithNewCreditCardReqDto.builder()
                        .number("2137420697776660")
                        .expirationDate("12/30")
                        .holderName("Jan Kowalski")
                        .amountGrosze(1L)
                        .cvc("123")
                        .build();

        // when
        var result = sut.rechargeToCreditCardDto(dto);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .isInstanceOf(SaveCreditCardReqDto.class)
                .hasFieldOrPropertyWithValue("label", null)
                .hasFieldOrPropertyWithValue("number", dto.getNumber())
                .hasFieldOrPropertyWithValue("expirationDate", dto.getExpirationDate())
                .hasFieldOrPropertyWithValue("holderName", dto.getHolderName());
    }
}
