package org.pwr.onlinecityticketsbackend.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.pwr.onlinecityticketsbackend.model.CreditCardInfo;

public class CreditCardInfoMapperTest {
    private final CreditCardInfoMapper sut = Mappers.getMapper(CreditCardInfoMapper.class);

    @Test
    public void shouldMapCreditCardInfoToCreditCardDto() {
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
                .hasFieldOrPropertyWithValue("id", model.getId())
                .hasFieldOrPropertyWithValue("label", model.getLabel())
                .hasFieldOrPropertyWithValue("lastFourDigits", "6660")
                .hasFieldOrPropertyWithValue("expirationDate", model.getExpirationDate())
                .hasFieldOrPropertyWithValue("holderName", model.getHolderName());
    }
}
