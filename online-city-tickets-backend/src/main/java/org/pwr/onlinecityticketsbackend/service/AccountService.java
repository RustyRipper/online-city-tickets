package org.pwr.onlinecityticketsbackend.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.account.AccountDto;
import org.pwr.onlinecityticketsbackend.dto.account.UpdateAccountReqDto;
import org.pwr.onlinecityticketsbackend.exception.AccountNotFound;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationInvalidRequest;
import org.pwr.onlinecityticketsbackend.mapper.AccountMapper;
import org.pwr.onlinecityticketsbackend.model.*;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.InspectorRepository;
import org.pwr.onlinecityticketsbackend.repository.PassengerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;
    private final PassengerRepository passengerRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final InspectorRepository inspectorRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Passenger createPassenger(
            String email, String fullname, String phoneNumber, String password) {
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

    public AccountDto getCurrentAccountByEmail() throws AccountNotFound {
        Account account = RequestContext.getAccountFromRequest();
        if (account.getRole().equals(Role.ADMIN)) {
            throw new AccountNotFound();
        }
        return accountMapper.toDto(getAccountByEmail(account.getEmail()));
    }

    public Account getAccountByEmail(String email) throws AccountNotFound {
        return accountRepository.findByEmail(email).orElseThrow(AccountNotFound::new);
    }

    public boolean isEmailInUse(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    public Account getAccountById(Long id) throws AccountNotFound {
        return accountRepository.findById(id).orElseThrow(AccountNotFound::new);
    }

    public AccountDto updateAccount(UpdateAccountReqDto updateAccountReqDto)
            throws AuthenticationInvalidRequest, AccountNotFound {
        Account account = RequestContext.getAccountFromRequest();
        if (account.getRole().equals(Role.ADMIN)) {
            throw new AuthenticationInvalidRequest();
        }
        if (account.getRole().equals(Role.INSPECTOR)
                && updateAccountReqDto.getPhoneNumber() != null) {
            throw new AuthenticationInvalidRequest();
        }
        account.setFullName(
                isFullNameInvalid(updateAccountReqDto.getFullName())
                        ? account.getFullName()
                        : updateAccountReqDto.getFullName());

        if (updateAccountReqDto.getNewPassword() != null) {
            if (isPasswordInvalid(updateAccountReqDto.getNewPassword())) {
                throw new AuthenticationInvalidRequest();
            }
            account.setPassword(passwordEncoder.encode(updateAccountReqDto.getNewPassword()));
        }

        if (account instanceof Passenger passenger) {
            passenger.setPhoneNumber(
                    isPhoneInvalid(updateAccountReqDto.getPhoneNumber())
                            ? passenger.getPhoneNumber()
                            : updateAccountReqDto.getPhoneNumber());
        }
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    private static boolean isPhoneInvalid(String phoneNumber) {
        return phoneNumber == null || !phoneNumber.matches("[0-9]{9}");
    }

    public static boolean isEmailInvalid(String email) {
        return email == null || email.length() < 3 || !email.contains("@");
    }

    public static boolean isPasswordInvalid(String password) {
        return password == null || password.length() < 8;
    }

    public static boolean isFullNameInvalid(String fullName) {
        return fullName == null || fullName.isEmpty();
    }
}
