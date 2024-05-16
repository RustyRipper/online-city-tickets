package org.pwr.onlinecityticketsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public sealed abstract class BaseTicketOfferDto
        permits SingleFareTicketOfferDto, TimeLimitedTicketOfferDto, LongTermTicketOfferDto {
    private long id;
    private String displayNameEn;
    private String displayNamePl;
    private String kind;
    private int priceGrosze;

    public abstract String getScope();
}
