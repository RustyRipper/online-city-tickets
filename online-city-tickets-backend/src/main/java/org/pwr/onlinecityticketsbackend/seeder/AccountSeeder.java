package org.pwr.onlinecityticketsbackend.seeder;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.model.Admin;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.service.AccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountSeeder {
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public void seedAccounts() {
        accountRepository.save(
                Admin.builder()
                        .email("admin@tickets.pl")
                        .password(new BCryptPasswordEncoder().encode("12345678"))
                        .fullName("Mr Admin")
                        .build());

        accountService.createPassenger(
                "passenger@tickets.pl", "Mr Passenger", "123456789", "12345678");
        accountRepository.save(
                Passenger.builder()
                        .email("richGuy@tickets.pl")
                        .password(new BCryptPasswordEncoder().encode("12345678"))
                        .walletBalancePln(BigDecimal.valueOf(10.0))
                        .fullName("Rich Guy")
                        .build());

        accountService.createInspector("inspector@tickets.pl", "Mr Inspector", "12345678");
    }
}
