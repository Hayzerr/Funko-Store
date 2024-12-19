package com.bolashak.onlinestorebackend.bootstrap;

import com.bolashak.onlinestorebackend.entities.Role;
import com.bolashak.onlinestorebackend.entities.User;
import com.bolashak.onlinestorebackend.entities.enums.RoleEnum;
import com.bolashak.onlinestorebackend.repository.RoleRepository;
import com.bolashak.onlinestorebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdmin();
    }

    private void createSuperAdmin() {
        Role role = roleRepository.findByName(RoleEnum.SUPER_ADMIN).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(RoleEnum.SUPER_ADMIN);
            newRole.setDescription("Administrator role with full privileges");
            return roleRepository.save(newRole);
        });

        Optional<User> optionalUser = userRepository.findByEmail("admin@mail.ru");

        if(optionalUser.isPresent()){
            return;
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@mail.ru");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(role);
        userRepository.save(admin);
    }
}