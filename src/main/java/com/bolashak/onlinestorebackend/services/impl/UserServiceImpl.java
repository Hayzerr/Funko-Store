package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.entities.User;
import com.bolashak.onlinestorebackend.repository.UserRepository;
import com.bolashak.onlinestorebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequestMapping
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;



    @Override
    public User createUser(User user){
        return userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());
        return userRepository.save(user);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        return null;
    }

}