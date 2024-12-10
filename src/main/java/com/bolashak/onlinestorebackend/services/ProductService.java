package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.entities.Product;

public interface ProductService {
    Iterable<Product> list();
    Iterable <Product> getByCategory(String category);
    Iterable <Product> getByFeature(String feature);
    Iterable <Product> getByStatus(String status);
    Iterable <Product> getAll();
    Product getById(String id);
    Iterable <Product> getByName(String Name);
    Iterable <Product> getAll(String status);
    Iterable <Product> getByStock(String stock);
    Iterable <Product> getByType(String type);
}
