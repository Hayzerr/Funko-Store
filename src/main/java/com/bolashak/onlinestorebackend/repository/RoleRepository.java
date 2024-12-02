package com.bolashak.onlinestorebackend.repository;

import com.bolashak.onlinestorebackend.entities.Role;
import com.bolashak.onlinestorebackend.entities.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}