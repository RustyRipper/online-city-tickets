package org.pwr.onlinecityticketsbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SingleFareTicketOfferDto extends BaseTicketOfferDto {
    private final String scope = "single-fare";
}
