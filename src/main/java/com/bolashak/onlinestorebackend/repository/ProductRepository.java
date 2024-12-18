package com.bolashak.onlinestorebackend.repository;

import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.entities.enums.ProductCategory;
import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.bolashak.onlinestorebackend.entities.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByCategory(ProductCategory category);
    Optional<Product> findByFeature(ProductFeature feature);
    Optional<Product> findByStatus(ProductStatus status);
    Optional<Product> findById(String id);
    Optional<Product> findByName(String Name);
    Optional<Product> findByStock(Integer stock);
    Optional<Product> findByProductType(ProductType type);


}