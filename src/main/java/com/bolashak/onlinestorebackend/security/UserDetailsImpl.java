package com.bolashak.onlinestorebackend.security;

import com.bolashak.onlinestorebackend.entities.Role;
import com.bolashak.onlinestorebackend.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private boolean active;
    private Collection<? extends GrantedAuthority> authorities;
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().name());

        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public static UserDetailsImpl fromUserEntityToCustomUserDetails(User user) {
        return new UserDetailsImpl(
                user.getUsername(),
                user.getPassword(),
                !user.isDeleted(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName().name())),
                user.getRole()
        );
    }
}
