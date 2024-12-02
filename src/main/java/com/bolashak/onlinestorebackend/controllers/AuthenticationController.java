package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.dto.request.RegisterRequest;
import com.bolashak.onlinestorebackend.dto.response.UserResponse;
import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import com.bolashak.onlinestorebackend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        UserResponse registeredUser = authenticationService.register(registerRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            AuthenticationResponse response = authenticationService.login(username, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        try {
            AuthenticationResponse response = authenticationService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String refreshToken) {
        authenticationService.logout(refreshToken);
        return ResponseEntity.ok("Logout successful");
    }
}