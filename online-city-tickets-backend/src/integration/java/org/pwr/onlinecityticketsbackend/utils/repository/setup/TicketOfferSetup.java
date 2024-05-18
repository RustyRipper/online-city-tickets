package org.pwr.onlinecityticketsbackend.utils.repository.setup;

import org.pwr.onlinecityticketsbackend.model.LongTermOffer;
import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.pwr.onlinecityticketsbackend.model.TimeLimitedOffer;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.pwr.onlinecityticketsbackend.utils.generator.TicketOfferGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketOfferSetup {
    @Autowired
    private TicketOfferRepository ticketOfferRepository;

    public SingleFareOffer setupSingleFareOffer(boolean isActive) {
        var singleFareOffer = TicketOfferGenerator.generateSingleFareOffer();
        singleFareOffer.setActive(isActive);
        return ticketOfferRepository.save(singleFareOffer);
    }

    public SingleFareOffer setupSingleFareOffer() {
        return setupSingleFareOffer(true);
    }

    public TimeLimitedOffer setupTimeLimitedOffer(boolean isActive) {
        var timeLimitedOffer = TicketOfferGenerator.generateTimeLimitedOffer();
        timeLimitedOffer.setActive(isActive);
        return ticketOfferRepository.save(timeLimitedOffer);
    }

    public TimeLimitedOffer setupTimeLimitedOffer() {
        return setupTimeLimitedOffer(true);
    }

    public LongTermOffer setupLongTermOffer(boolean isActive) {
        var longTermOffer = TicketOfferGenerator.generateLongTermOffer();
        longTermOffer.setActive(isActive);
        return ticketOfferRepository.save(longTermOffer);
    }

    public LongTermOffer setupLongTermOffer() {
        return setupLongTermOffer(true);
    }
}
