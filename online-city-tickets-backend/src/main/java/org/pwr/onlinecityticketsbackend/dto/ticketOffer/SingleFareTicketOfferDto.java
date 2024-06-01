package org.pwr.onlinecityticketsbackend.dto.ticketOffer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public final class SingleFareTicketOfferDto extends BaseTicketOfferDto {
    private final String scope = "single-fare";
}
