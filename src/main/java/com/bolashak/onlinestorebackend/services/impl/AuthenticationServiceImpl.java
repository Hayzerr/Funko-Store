package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import com.bolashak.onlinestorebackend.dto.request.RegisterRequest;
import com.bolashak.onlinestorebackend.dto.response.UserResponse;
import com.bolashak.onlinestorebackend.entities.RefreshToken;
import com.bolashak.onlinestorebackend.entities.Role;
import com.bolashak.onlinestorebackend.entities.User;
import com.bolashak.onlinestorebackend.entities.enums.RoleEnum;
import com.bolashak.onlinestorebackend.repository.RefreshTokenRepository;
import com.bolashak.onlinestorebackend.repository.RoleRepository;
import com.bolashak.onlinestorebackend.repository.UserRepository;
import com.bolashak.onlinestorebackend.security.JwtUtil;
import com.bolashak.onlinestorebackend.services.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000;  // 15 minutes
    private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;  // 7 days
    private final RoleRepository roleRepository;

    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        Optional<Role> roleOptional = roleRepository.findByName(RoleEnum.USER);

        if(roleOptional.isEmpty()){
            throw new IllegalArgumentException("Role not found");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setDeleted(false);
        user.setRole(roleOptional.get());
        log.info("User registered: {}", user);
        User registeredUser = userRepository.save(user);

        UserResponse userResponse = modelMapper.map(registeredUser, UserResponse.class);
        return userResponse;
    }

    @Transactional
    public AuthenticationResponse login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtUtil.generateToken(userDetails.getUsername(), ACCESS_TOKEN_VALIDITY);
        String refreshTokenStr = jwtUtil.generateToken(userDetails.getUsername(), REFRESH_TOKEN_VALIDITY);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenStr);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY));
        refreshTokenRepository.save(refreshToken);

        return new AuthenticationResponse(accessToken, refreshTokenStr);
    }

    @Transactional
    public AuthenticationResponse refreshAccessToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateToken(token.getUser().getUsername(), ACCESS_TOKEN_VALIDITY);
        return new AuthenticationResponse(newAccessToken, refreshToken);
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}