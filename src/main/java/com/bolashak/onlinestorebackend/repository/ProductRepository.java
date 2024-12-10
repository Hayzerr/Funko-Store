package com.bolashak.onlinestorebackend.repository;

import com.bolashak.onlinestorebackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(String id);
    Optional<Product> findByName(String name);

}