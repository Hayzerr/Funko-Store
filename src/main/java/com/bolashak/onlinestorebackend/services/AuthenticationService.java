package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import com.bolashak.onlinestorebackend.dto.request.RegisterRequest;
import com.bolashak.onlinestorebackend.dto.response.UserResponse;
import com.bolashak.onlinestorebackend.entities.User;

public interface AuthenticationService {
    void logout(String refreshToken);
    AuthenticationResponse refreshAccessToken(String refreshToken);
    AuthenticationResponse login(String username, String password);
    UserResponse register(RegisterRequest registerRequest);
}