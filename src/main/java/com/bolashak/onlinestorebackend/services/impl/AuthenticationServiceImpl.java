package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.dto.request.RegisterRequest;
import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import com.bolashak.onlinestorebackend.dto.response.UserResponse;
import com.bolashak.onlinestorebackend.entities.ConfirmationToken;
import com.bolashak.onlinestorebackend.entities.RefreshToken;
import com.bolashak.onlinestorebackend.entities.Role;
import com.bolashak.onlinestorebackend.entities.User;
import com.bolashak.onlinestorebackend.entities.enums.RoleEnum;
import com.bolashak.onlinestorebackend.repository.ConfirmationTokenRepository;
import com.bolashak.onlinestorebackend.repository.RefreshTokenRepository;
import com.bolashak.onlinestorebackend.repository.RoleRepository;
import com.bolashak.onlinestorebackend.repository.UserRepository;
import com.bolashak.onlinestorebackend.security.JwtUtil;
import com.bolashak.onlinestorebackend.services.AuthenticationService;
import com.bolashak.onlinestorebackend.services.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
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

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;

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
        user = userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setConfirmationToken(java.util.UUID.randomUUID().toString());
        confirmationToken.setCreatedDate(new Date());
        confirmationTokenRepository.save(confirmationToken);
        user.setVerificationCode(confirmationToken.getConfirmationToken());
        userRepository.save(user);


        String recipientEmail = user.getEmail();
        String subject = "Complete Registration!";
        String text = "To confirm your account, please click here: " +
                "https://funko-store.onrender.com/api/auth/confirm-email?token=" + confirmationToken.getConfirmationToken();

        emailService.sendEmail(recipientEmail, subject, text);

        log.info("Confirmation email sent to: {}", user.getEmail());

        return modelMapper.map(user, UserResponse.class);
    }
    @Transactional
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        log.info("User email confirmed: {}", user.getEmail());

        return ResponseEntity.ok("Email verified successfully!");
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