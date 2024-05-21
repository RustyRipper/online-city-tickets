package org.pwr.onlinecityticketsbackend.auth;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.model.ErrorResponse;
import org.pwr.onlinecityticketsbackend.service.AccountService;
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

    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register/passenger")
    public ResponseEntity<?> register(@RequestBody RegisterRequestPassenger registerRequest) {
        if (isRegisterRequestInvalid(registerRequest)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Invalid request body."), HttpStatus.BAD_REQUEST);
        }

        if (accountService.isEmailInUse(registerRequest.getEmail())) {
            return new ResponseEntity<>(
                    new ErrorResponse("Email already in use."), HttpStatus.CONFLICT);
        }

        AuthenticationResponse register = authenticationService.registerPassenger(registerRequest);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/inspector")
    public ResponseEntity<?> registerInspector(@RequestBody RegisterRequest registerRequest) {
        if (isRegisterRequestInvalid(registerRequest)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Invalid request body."), HttpStatus.BAD_REQUEST);
        }

        if (accountService.isEmailInUse(registerRequest.getEmail())) {
            return new ResponseEntity<>(
                    new ErrorResponse("Email already in use."), HttpStatus.CONFLICT);
        }

        AuthenticationResponse register = authenticationService.registerInspector(registerRequest);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Invalid credentials."), HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: add more validation
    private static boolean isRegisterRequestInvalid(RegisterRequest registerRequest) {
        return registerRequest.getEmail() == null
                || registerRequest.getFullName() == null
                || registerRequest.getPassword() == null;
    }
}
