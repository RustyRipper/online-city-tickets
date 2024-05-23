package org.pwr.onlinecityticketsbackend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.exception.AccountNotFound;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.service.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerPassenger(RegisterRequestPassenger registerRequest) {
        return buildAuthenticationResponse(
                accountService.createPassenger(
                        registerRequest.getEmail(),
                        registerRequest.getFullName(),
                        registerRequest.getPhoneNumber(),
                        registerRequest.getPassword()));
    }

    public AuthenticationResponse registerInspector(RegisterRequest registerRequest) {
        return buildAuthenticationResponse(
                accountService.createInspector(
                        registerRequest.getEmail(),
                        registerRequest.getFullName(),
                        registerRequest.getPassword()));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest)
            throws AccountNotFound {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        return buildAuthenticationResponse(
                accountService.getAccountByEmail(authenticationRequest.getEmail()));
    }

    private AuthenticationResponse buildAuthenticationResponse(Account account) {
        Claims claims = Jwts.claims().setSubject(account.getUsername());
        claims.put("userId", account.getId().toString());
        claims.put("role", account.getRole());
        return AuthenticationResponse.builder()
                .jwt(jwtService.generateToken(claims, account))
                .build();
    }
}
