package com.bolashak.onlinestorebackend.dto.request;

import com.bolashak.onlinestorebackend.entities.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
    private String password;
}