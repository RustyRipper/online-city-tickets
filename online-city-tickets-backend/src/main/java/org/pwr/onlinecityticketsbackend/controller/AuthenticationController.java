package org.pwr.onlinecityticketsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.authentication.AuthenticationRequestDto;
import org.pwr.onlinecityticketsbackend.dto.authentication.RegisterRequestDto;
import org.pwr.onlinecityticketsbackend.dto.authentication.RegisterRequestPassengerDto;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationEmailInUse;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationInvalidRequest;
import org.pwr.onlinecityticketsbackend.exception.UnauthorizedUser;
import org.pwr.onlinecityticketsbackend.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/passenger")
    public ResponseEntity<?> register(@RequestBody RegisterRequestPassengerDto registerRequest)
            throws AuthenticationEmailInUse, AuthenticationInvalidRequest {
        return new ResponseEntity<>(
                authenticationService.registerPassenger(registerRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/inspector")
    public ResponseEntity<?> registerInspector(@RequestBody RegisterRequestDto registerRequestDto)
            throws AuthenticationEmailInUse, AuthenticationInvalidRequest {
        return new ResponseEntity<>(
                authenticationService.registerInspector(registerRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequestDto authenticationRequestDto)
            throws UnauthorizedUser {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDto));
    }
}
