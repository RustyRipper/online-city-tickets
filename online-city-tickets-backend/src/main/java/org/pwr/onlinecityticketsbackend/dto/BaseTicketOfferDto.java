package org.pwr.onlinecityticketsbackend.dto;

import org.pwr.onlinecityticketsbackend.model.TicketKind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTicketOfferDto {
    private long id;
    private String displayNameEn;
    private String displayNamePl;
    private TicketKind kind;
    private int priceGrosze;
    private String scope;
}
