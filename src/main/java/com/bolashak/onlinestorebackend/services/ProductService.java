package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.enums.ProductCategory;
import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.bolashak.onlinestorebackend.entities.enums.ProductType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<ProductDto>> list();
    Iterable <ProductDto> getByCategory(ProductCategory category);
    Iterable <ProductDto > getByFeature(ProductFeature feature);
    Iterable <ProductDto > getByStatus(ProductStatus status);
    Iterable <ProductDto > getAll();
    ProductDto  getById(String id);
    Iterable <ProductDto > getByName(String Name);
    Iterable <ProductDto > getByStock(Integer stock);
    Iterable <ProductDto > getByType(ProductType type);
}
