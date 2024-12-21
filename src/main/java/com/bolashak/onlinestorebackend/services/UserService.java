package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import com.bolashak.onlinestorebackend.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getCurrentUser();
    User getUserByUsername(String username);
}
