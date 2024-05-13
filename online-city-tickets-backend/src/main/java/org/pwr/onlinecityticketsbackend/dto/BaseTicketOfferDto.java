package org.pwr.onlinecityticketsbackend.dto;

import org.pwr.onlinecityticketsbackend.model.TicketKind;

public class BaseTicketOfferDto {
    private long id;
    private String displayNameEn;
    private String displayNamePl;
    private TicketKind kind;
    private int priceGrosze;
}
