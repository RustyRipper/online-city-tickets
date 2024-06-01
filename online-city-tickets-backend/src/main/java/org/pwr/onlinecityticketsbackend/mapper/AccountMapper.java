package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import org.mapstruct.*;
import org.pwr.onlinecityticketsbackend.dto.account.AccountDto;
import org.pwr.onlinecityticketsbackend.dto.account.InspectorDto;
import org.pwr.onlinecityticketsbackend.dto.account.PassengerDto;
import org.pwr.onlinecityticketsbackend.model.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    default AccountDto toDto(Account account) {
        return switch (account) {
            case Passenger passenger -> toPassengerDto(passenger);
            case Inspector inspector -> toInspectorDto(inspector);
            default -> throw new IllegalStateException();
        };
    }

    @Mapping(source = "role", target = "type", qualifiedByName = "roleToType")
    @Mapping(
            source = "walletBalancePln",
            target = "walletBalanceGrosze",
            qualifiedByName = "pricePlnToGrosze")
    PassengerDto toPassengerDto(Passenger passenger);

    @Mapping(source = "role", target = "type", qualifiedByName = "roleToType")
    InspectorDto toInspectorDto(Inspector inspector);

    @Named("roleToType")
    default String roleToType(Role role) {
        return role.name().toLowerCase();
    }

    @Named("pricePlnToGrosze")
    default int pricePlnToGrosze(BigDecimal pricePln) {
        return pricePln.multiply(BigDecimal.valueOf(100)).intValue();
    }
}
