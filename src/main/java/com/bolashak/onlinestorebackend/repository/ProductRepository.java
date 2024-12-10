package com.bolashak.onlinestorebackend.repository;

import com.bolashak.onlinestorebackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByCategory(String category);
    Optional<Product> findByFeature(String feature);
    Optional<Product> findByStatus(String status);
    Optional<Product> findById(String id);
    Optional<Product> findByName(String Name);
    Optional<Product> findByStock(String stock);
    Optional<Product> findByProductType(String type);


}