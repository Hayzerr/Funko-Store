package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.request.RegisterRequest;
import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import com.bolashak.onlinestorebackend.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> confirmEmail(String confirmationToken);
    void logout(String refreshToken);
    AuthenticationResponse refreshAccessToken(String refreshToken);
    AuthenticationResponse login(String username, String password);
    UserResponse register(RegisterRequest registerRequest);
}