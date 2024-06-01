package org.pwr.onlinecityticketsbackend.mapper;

import org.mapstruct.*;
import org.pwr.onlinecityticketsbackend.dto.ticket.ValidationDto;
import org.pwr.onlinecityticketsbackend.model.Validation;
import org.pwr.onlinecityticketsbackend.model.Vehicle;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ValidationMapper {
    @Mapping(
            source = "vehicle",
            target = "vehicleSideNumber",
            qualifiedByName = "vehicleSideNumber")
    ValidationDto toDto(Validation validation);

    @Named("vehicleSideNumber")
    default String vehicleToVehicleSideNumber(Vehicle vehicle) {
        return vehicle.getSideNumber();
    }
}
