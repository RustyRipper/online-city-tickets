package org.pwr.onlinecityticketsbackend.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.CreditCardDto;
import org.pwr.onlinecityticketsbackend.model.CreditCardInfo;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CreditCardInfoMapper {

    @Mapping(
            source = "cardNumber",
            target = "lastFourDigits",
            qualifiedByName = "cardNumberToLastFourDigits")
    CreditCardDto toDto(CreditCardInfo creditCardInfo);

    @Named("cardNumberToLastFourDigits")
    default String cardNumberToLastFourDigits(String cardNumber) {
        return cardNumber.substring(cardNumber.length() - 4);
    }
}
