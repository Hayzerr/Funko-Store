package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.repository.ProductRepository;
import com.bolashak.onlinestorebackend.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Iterable<Product> list() {
        return productRepository.findAll();
    }
    public Iterable<Product> save(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public List<Product> getByCategory(String category) {
        return null;
    }

    @Override
    public List<Product> getByFeature(String feature) {
        return null;
    }

    @Override
    public List<Product> getByStatus(String status) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product getById(String id) {
        return null;
    }

    @Override
    public List<Product> getByName(String Name) {
        return null;
    }

    @Override
    public List<Product> getAll(String status) {
        return null;
    }

    @Override
    public List<Product> getByStock(String stock) {
        return null;
    }

    @Override
    public List<Product> getByType(String type) {
        return null;
    }
}