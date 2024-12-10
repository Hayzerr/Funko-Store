package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Iterable<Product> list() {
        return productRepository.findAll();
    }
    public Iterable<Product> save(List<Product> products) {
        return productRepository.saveAll(products);
    }
}