package org.pwr.onlinecityticketsbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.auth.*;
import org.pwr.onlinecityticketsbackend.dto.AuthenticationRequestDto;
import org.pwr.onlinecityticketsbackend.dto.AuthenticationResponseDto;
import org.pwr.onlinecityticketsbackend.dto.RegisterRequestDto;
import org.pwr.onlinecityticketsbackend.dto.RegisterRequestPassengerDto;
import org.pwr.onlinecityticketsbackend.exception.AccountNotFound;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationEmailInUse;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationInvalidRequest;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto registerPassenger(
            RegisterRequestPassengerDto registerRequestPassengerDto)
            throws AuthenticationEmailInUse, AuthenticationInvalidRequest {
        if (isRegisterRequestInvalid(registerRequestPassengerDto)) {
            throw new AuthenticationInvalidRequest();
        }
        if (accountService.isEmailInUse(registerRequestPassengerDto.getEmail())) {
            throw new AuthenticationEmailInUse();
        }

        return buildAuthenticationResponse(
                accountService.createPassenger(
                        registerRequestPassengerDto.getEmail(),
                        registerRequestPassengerDto.getFullName(),
                        registerRequestPassengerDto.getPhoneNumber(),
                        registerRequestPassengerDto.getPassword()));
    }

    public AuthenticationResponseDto registerInspector(RegisterRequestDto registerRequestDto)
            throws AuthenticationInvalidRequest, AuthenticationEmailInUse {
        if (isRegisterRequestInvalid(registerRequestDto)) {
            throw new AuthenticationInvalidRequest();
        }
        if (accountService.isEmailInUse(registerRequestDto.getEmail())) {
            throw new AuthenticationEmailInUse();
        }

        return buildAuthenticationResponse(
                accountService.createInspector(
                        registerRequestDto.getEmail(),
                        registerRequestDto.getFullName(),
                        registerRequestDto.getPassword()));
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto)
            throws AccountNotFound {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getEmail(),
                        authenticationRequestDto.getPassword()));
        return buildAuthenticationResponse(
                accountService.getAccountByEmail(authenticationRequestDto.getEmail()));
    }

    private AuthenticationResponseDto buildAuthenticationResponse(Account account) {
        Claims claims = Jwts.claims().setSubject(account.getUsername());
        claims.put("userId", account.getId().toString());
        claims.put("role", account.getRole());
        return AuthenticationResponseDto.builder()
                .jwt(jwtService.generateToken(claims, account))
                .build();
    }

    private static boolean isRegisterRequestInvalid(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.getEmail() == null
                || registerRequestDto.getEmail().length() < 3
                || !registerRequestDto.getEmail().contains("@")
                || registerRequestDto.getPassword() == null
                || registerRequestDto.getPassword().length() < 6
                || registerRequestDto.getFullName() == null
                || registerRequestDto.getFullName().length() < 3;
    }
}
