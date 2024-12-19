package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.enums.ProductCategory;
import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.bolashak.onlinestorebackend.entities.enums.ProductType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    ProductDto getById(String id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(String id, ProductDto productDto);
    void deleteProduct(String id);

    List<ProductDto> getByCategory(ProductCategory category);
    List<ProductDto> getByFeature(ProductFeature feature);
    List<ProductDto> getByStatus(ProductStatus status);
    List<ProductDto> getByName(String name);
    List<ProductDto> getByType(ProductType type);
    List<ProductDto> getFilteredProducts(ProductCategory category, ProductFeature feature, ProductStatus status,
                                        ProductType type, String name, int page, int size);
}
