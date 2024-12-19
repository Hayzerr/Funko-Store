package com.bolashak.onlinestorebackend.repository;

import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.entities.enums.ProductCategory;
import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.bolashak.onlinestorebackend.entities.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByCategory(ProductCategory category);
    List<Product> findByFeature(ProductFeature feature);
    List<Product> findByStatus(ProductStatus status);
    Optional<Product> findById(String id);
    List<Product> findByName(String Name);
    Optional<Product> findByStock(Integer stock);
    List<Product> findByProductType(ProductType type);

    @Query("SELECT p FROM Product p WHERE " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:feature IS NULL OR p.feature = :feature) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:type IS NULL OR p.productType = :type) AND " +
            "(:name IS NULL OR p.name LIKE :name)")
    Page<Product> findFilteredProducts(
            @Param("category") ProductCategory category,
            @Param("feature") ProductFeature feature,
            @Param("status") ProductStatus status,
            @Param("type") ProductType type,
            @Param("name") String name,
            Pageable pageable);

}