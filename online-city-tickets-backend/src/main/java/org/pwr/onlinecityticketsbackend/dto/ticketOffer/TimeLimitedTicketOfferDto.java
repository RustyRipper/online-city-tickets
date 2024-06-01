package org.pwr.onlinecityticketsbackend.dto.ticketOffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class TimeLimitedTicketOfferDto extends BaseTicketOfferDto {
    private final String scope = "time-limited";
    private int durationMinutes;
}
