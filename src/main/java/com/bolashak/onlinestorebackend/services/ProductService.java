package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getByCategory(String category);
    List<Product> getByFeature(String feature);
    List<Product> getByStatus(String status);
    List<Product> getAll();
    Product getById(String id);
    List<Product> getByName(String Name);
    List<Product> getAll(String status);
    List<Product> getByStock(String stock);
    List<Product> getByType(String type);
}
