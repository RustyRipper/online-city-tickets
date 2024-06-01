package org.pwr.onlinecityticketsbackend.utils.repository.setup;

import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.utils.generator.AccountGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountSetup {
    @Autowired private AccountRepository accountRepository;

    public Passenger setupPassenger() {
        var passenger = AccountGenerator.generatePassenger();
        return accountRepository.saveAndFlush(passenger);
    }

    public Inspector setupInspector() {
        var inspector = AccountGenerator.generateInspector();
        return accountRepository.saveAndFlush(inspector);
    }
}
