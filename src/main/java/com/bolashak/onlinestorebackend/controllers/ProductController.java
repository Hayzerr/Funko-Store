package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/list")
    public Iterable<Product> list() {
        return productService.list();
    }
}