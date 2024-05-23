package org.pwr.onlinecityticketsbackend.auth;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.exception.AccountNotFound;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationEmailInUse;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationInvalidRequest;
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
    public ResponseEntity<?> register(@RequestBody RegisterRequestPassenger registerRequest)
            throws AuthenticationInvalidRequest, AuthenticationEmailInUse {
        if (isRegisterRequestInvalid(registerRequest)) {
            throw new AuthenticationInvalidRequest();
        }
        if (accountService.isEmailInUse(registerRequest.getEmail())) {
            throw new AuthenticationEmailInUse();
        }

        AuthenticationResponse register = authenticationService.registerPassenger(registerRequest);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/inspector")
    public ResponseEntity<?> registerInspector(@RequestBody RegisterRequest registerRequest)
            throws AuthenticationInvalidRequest, AuthenticationEmailInUse {
        if (isRegisterRequestInvalid(registerRequest)) {
            throw new AuthenticationInvalidRequest();
        }
        if (accountService.isEmailInUse(registerRequest.getEmail())) {
            throw new AuthenticationEmailInUse();
        }

        AuthenticationResponse register = authenticationService.registerInspector(registerRequest);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest)
            throws AccountNotFound {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    // TODO: add more validation
    private static boolean isRegisterRequestInvalid(RegisterRequest registerRequest) {
        return registerRequest.getEmail() == null
                || registerRequest.getFullName() == null
                || registerRequest.getPassword() == null;
    }
}
