package com.bolashak.onlinestorebackend.dto.response;

import com.bolashak.onlinestorebackend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Role role;
}