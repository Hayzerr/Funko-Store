package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.entities.User;

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
