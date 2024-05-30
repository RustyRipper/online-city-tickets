package org.pwr.onlinecityticketsbackend.utils.repository.setup;

import org.pwr.onlinecityticketsbackend.model.CreditCardInfo;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.pwr.onlinecityticketsbackend.utils.generator.CreditCardInfoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCardInfoSetup {
    @Autowired private CreditCardInfoRepository creditCardInfoRepository;

    public CreditCardInfo setupCreditCardInfo(Passenger owner) {
        var creditCardInfo = CreditCardInfoGenerator.generateCreditCardInfo();
        creditCardInfo.setOwner(owner);
        return creditCardInfoRepository.saveAndFlush(creditCardInfo);
    }
}
