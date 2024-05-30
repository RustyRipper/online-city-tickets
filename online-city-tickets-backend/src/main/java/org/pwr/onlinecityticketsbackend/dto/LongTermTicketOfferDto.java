package org.pwr.onlinecityticketsbackend.dto;

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
public final class LongTermTicketOfferDto extends BaseTicketOfferDto {
    private final String scope = "long-term";
    private int validDays;
}
