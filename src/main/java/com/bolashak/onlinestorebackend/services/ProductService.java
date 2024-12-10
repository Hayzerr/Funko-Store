package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<ProductDto>> list();
    Iterable <ProductDto> getByCategory(String category);
    Iterable <ProductDto > getByFeature(String feature);
    Iterable <ProductDto > getByStatus(String status);
    Iterable <ProductDto > getAll();
    ProductDto  getById(String id);
    Iterable <ProductDto > getByName(String Name);
    Iterable <ProductDto > getByStock(String stock);
    Iterable <ProductDto > getByType(String type);
}
