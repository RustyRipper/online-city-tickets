package org.pwr.onlinecityticketsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    @PostMapping("/hello")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("JWT WORKS!");
    }
}
