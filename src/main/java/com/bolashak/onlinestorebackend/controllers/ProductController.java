package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.enums.ProductCategory;
import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.bolashak.onlinestorebackend.entities.enums.ProductType;
import com.bolashak.onlinestorebackend.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> list() {
        return productService.list();
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getByCategory(@PathVariable ProductCategory category) {
        return ResponseEntity.ok(productService.getByCategory(category));
    }
    @GetMapping("/feature/{feature}")
    public ResponseEntity<List<ProductDto>> getByFeature(@PathVariable ProductFeature feature) {
        return ResponseEntity.ok(productService.getByFeature(feature));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDto>> getByStatus(@PathVariable ProductStatus status) {
        return ResponseEntity.ok(productService.getByStatus(status));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDto>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getByName(name));
    }

    @GetMapping("/stock/{stock}")
    public ResponseEntity<List<ProductDto>> getByStock(@PathVariable Integer stock) {
        return ResponseEntity.ok(productService.getByStock(stock));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductDto>> getByType(@PathVariable ProductType type) {
        return ResponseEntity.ok(productService.getByType(type));
    }

}