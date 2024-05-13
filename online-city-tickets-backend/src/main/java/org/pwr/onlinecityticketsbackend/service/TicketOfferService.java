package org.pwr.onlinecityticketsbackend.service;

import java.util.List;

import org.pwr.onlinecityticketsbackend.dto.BaseTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.LongTermTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.SingleFareTicketOfferDto;
import org.pwr.onlinecityticketsbackend.dto.TimeLimitedTicketOfferDto;
import org.pwr.onlinecityticketsbackend.mapper.LongTermOfferMapper;
import org.pwr.onlinecityticketsbackend.mapper.SingleFareOfferMapper;
import org.pwr.onlinecityticketsbackend.mapper.TimeLimitedOfferMapper;
import org.pwr.onlinecityticketsbackend.repository.LongTermOfferRepository;
import org.pwr.onlinecityticketsbackend.repository.SingleFareOfferRepository;
import org.pwr.onlinecityticketsbackend.repository.TimeLimitedOfferRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketOfferService {
    private final SingleFareOfferRepository singleFareOfferRepository;
    private final LongTermOfferRepository longTermOfferRepository;
    private final TimeLimitedOfferRepository timeLimitedOfferRepository;

    private List<SingleFareTicketOfferDto> getSingleFareOffers() {
        var mapper = SingleFareOfferMapper.INSTANCE;
        var offers = singleFareOfferRepository.findAll();
        var dtos = offers.stream().map(mapper::toSingleFareTicketOfferDto).toList();
        return dtos;
    }

    private List<TimeLimitedTicketOfferDto> getTimeLimitedOffers() {
        var mapper = TimeLimitedOfferMapper.INSTANCE;
        var offers = timeLimitedOfferRepository.findAll();
        var dtos = offers.stream().map(mapper::toTimeLimitedTicketOfferDto).toList();
        return dtos;
    }

    private List<LongTermTicketOfferDto> getLongTermOffers() {
        var mapper = LongTermOfferMapper.INSTANCE;
        var offers = longTermOfferRepository.findAll();
        var dtos = offers.stream().map(mapper::toLongTermTicketOfferDto).toList();
        return dtos;
    }

    public List<? extends BaseTicketOfferDto> getOffers() {
        var singleFareOffers = getSingleFareOffers();
        var timeLimitedOffers = getTimeLimitedOffers();
        var longTermOffers = getLongTermOffers();
        return List.of(singleFareOffers, timeLimitedOffers, longTermOffers).stream().flatMap(List::stream).toList();
    }
}
