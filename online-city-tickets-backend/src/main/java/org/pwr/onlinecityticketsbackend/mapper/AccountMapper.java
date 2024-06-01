package org.pwr.onlinecityticketsbackend.mapper;

import java.math.BigDecimal;
import java.util.Collection;
import org.mapstruct.*;
import org.pwr.onlinecityticketsbackend.dto.account.AccountDto;
import org.pwr.onlinecityticketsbackend.dto.account.InspectorDto;
import org.pwr.onlinecityticketsbackend.dto.account.PassengerDto;
import org.pwr.onlinecityticketsbackend.model.*;
import org.springframework.security.core.GrantedAuthority;

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

    @Mapping(
            source = "walletBalancePln",
            target = "walletBalanceGrosze",
            qualifiedByName = "pricePlnToGrosze")
    @Mapping(target = "type", expression = "java(roleToType(passenger.getAuthorities()))")
    PassengerDto toPassengerDto(Passenger passenger);

    @Mapping(target = "type", expression = "java(roleToType(inspector.getAuthorities()))")
    InspectorDto toInspectorDto(Inspector inspector);

    @Named("roleToType")
    default String roleToType(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().toList().get(0).getAuthority().toLowerCase();
    }

    @Named("pricePlnToGrosze")
    default int pricePlnToGrosze(BigDecimal pricePln) {
        return pricePln.multiply(BigDecimal.valueOf(100)).intValue();
    }
}
