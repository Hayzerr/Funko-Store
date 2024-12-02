package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserByUsername(String username);
}