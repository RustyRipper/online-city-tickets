package org.pwr.onlinecityticketsbackend.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.CreditCardDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.SaveCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.model.CreditCardInfo;

public class CreditCardInfoMapperTest {
    private final CreditCardInfoMapper sut = Mappers.getMapper(CreditCardInfoMapper.class);

    @Test
    public void toDtoshouldMapCreditCardInfoToCreditCardDto() {
        // given
        var model =
                CreditCardInfo.builder()
                        .id(1L)
                        .label("Po to bóg dał oczy, żeby robić na oko")
                        .cardNumber("2137420697776660")
                        .expirationDate("12/30")
                        .holderName("Jan Kowalski")
                        .owner(null)
                        .build();

        // when
        var result = sut.toDto(model);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .isInstanceOf(CreditCardDto.class)
                .hasFieldOrPropertyWithValue("id", model.getId())
                .hasFieldOrPropertyWithValue("label", model.getLabel())
                .hasFieldOrPropertyWithValue(
                        "lastFourDigits",
                        model.getCardNumber().substring(model.getCardNumber().length() - 4))
                .hasFieldOrPropertyWithValue("expirationDate", model.getExpirationDate())
                .hasFieldOrPropertyWithValue("holderName", model.getHolderName());
    }

    @Test
    public void toEntityShouldMapSaveCreditCardReqDtoToCreditCardInfo() {
        // given
        var dto =
                new SaveCreditCardReqDto(
                        "Może i uczę się wolno, ale za to opornie",
                        "2137420697776660",
                        "12/30",
                        "Jan Kowalski");

        // when
        var result = sut.toEntity(dto);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .isInstanceOf(CreditCardInfo.class)
                .hasFieldOrPropertyWithValue("label", dto.getLabel())
                .hasFieldOrPropertyWithValue("cardNumber", dto.getNumber())
                .hasFieldOrPropertyWithValue("expirationDate", dto.getExpirationDate())
                .hasFieldOrPropertyWithValue("holderName", dto.getHolderName())
                .hasFieldOrPropertyWithValue("owner", null)
                .hasFieldOrPropertyWithValue("id", null);
    }
}
