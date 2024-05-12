package org.pwr.onlinecityticketsbackend.service;

import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataSeeder {
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public DataSeeder(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public void seedData() {
        accountRepository.save(Account.builder()
                .email("admin@tickets.pl")
                .role(Role.valueOf("ADMIN"))
                .password(new BCryptPasswordEncoder().encode("12345678"))
                .fullName("Mr Admin")
                .build());

        accountService.createPassenger("passenger@tickets.pl", "Mr Passenger", "123456789", "12345678");
        accountService.createInspector("inspector@tickets.pl", "Mr Inspector", "12345678");
    }
}
