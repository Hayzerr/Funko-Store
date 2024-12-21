package com.bolashak.onlinestorebackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "store_user")
public class User extends AbstractEntity<Long>{
    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean enabled = false;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Column
    private String address;

    @Column(nullable = false)
    private boolean isDeleted;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;
}