package org.pwr.onlinecityticketsbackend.service;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.InspectorRepository;
import org.pwr.onlinecityticketsbackend.repository.PassengerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final PassengerRepository passengerRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final InspectorRepository inspectorRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Inspector> getAllInspectors() {
        return inspectorRepository.findAll();
    }

    public Optional<Account> getCurrentAccountById(Long id){
        return accountRepository.findById(id);
    }

    public Passenger createPassenger(String email, String fullname, String phoneNumber, String password) {
        Passenger passenger = new Passenger();
        passenger.setFullName(fullname);
        passenger.setEmail(email);
        passenger.setPhoneNumber(phoneNumber);
        passenger.setPassword(passwordEncoder.encode(password));
        passenger.setRole(Role.PASSENGER);
        return passengerRepository.save(passenger);
    }

    public Inspector createInspector(String email, String fullname, String password) {
        Inspector inspector = new Inspector();
        inspector.setEmail(email);
        inspector.setFullName(fullname);
        inspector.setPassword(passwordEncoder.encode(password));
        inspector.setRole(Role.INSPECTOR);
        return inspectorRepository.save(inspector);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

    }
    public boolean isEmailInUse(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

    }
}
