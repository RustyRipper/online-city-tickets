package org.pwr.onlinecityticketsbackend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/passenger")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        AuthenticationResponse register = authenticationService.registerPassenger(registerRequest);
        return ResponseEntity.ok(register);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/inspector")
    public ResponseEntity<AuthenticationResponse> registerInspector(
            @RequestBody RegisterRequest registerRequest
    ) {
        AuthenticationResponse register = authenticationService.registerInspector(registerRequest);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}