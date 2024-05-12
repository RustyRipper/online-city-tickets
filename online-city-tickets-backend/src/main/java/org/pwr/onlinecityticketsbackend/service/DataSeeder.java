package org.pwr.onlinecityticketsbackend.service;

import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataSeeder {
    private final AccountRepository accountRepository;
    private final PassengerRepository passengerRepository;
    private final AccountService accountService;

    public DataSeeder(AccountRepository accountRepository, PassengerRepository passengerRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.passengerRepository = passengerRepository;
        this.accountService = accountService;
    }

    public void seedData() {

        accountRepository.save(Account.builder()
                .email("admin")
                .role(Role.valueOf("ADMIN"))
                .password(new BCryptPasswordEncoder().encode("admin"))
                .fullName("admin")
                .build());

        accountService.createPassenger("passenger", "passenger", "123456789", "passenger");
        accountService.createInspector("inspector", "inspector", "inspector");
    }
}
