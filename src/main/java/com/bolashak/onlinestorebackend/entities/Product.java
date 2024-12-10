package com.bolashak.onlinestorebackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @Column(length = 255)  // Ensures VARCHAR(255) in database
    @JsonProperty("Id")
    private String id;

    @Column(length = 1000)
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Feature")
    private String feature;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("DefaultPrice")
    private String defaultPrice;

    @JsonProperty("DiscountPrice")
    private String discountPrice;

    @JsonProperty("Link")
    private String link;

    @JsonProperty("Category")
    private String category;

    @JsonProperty("Stock")
    private String stock;

    @JsonProperty("License")
    private String license;

    @JsonProperty("ProductType")
    private String productType;

    @Column(length = 2000)
    @JsonProperty("Description")
    private String description;

    @ElementCollection
    @JsonProperty("Images")
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images;


    public Product() {}
}