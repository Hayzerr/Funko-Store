package com.bolashak.onlinestorebackend.entities;

import com.bolashak.onlinestorebackend.entities.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @Column(length = 255, unique = true, nullable = false)
    private String id;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    @Column(length = 1000, nullable = false)
    @JsonProperty("Name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 500)
    @JsonProperty("Feature")
    private ProductFeature feature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status is required")
    @JsonProperty("Status")
    private ProductStatus status;

    @Column(nullable = false)
    @JsonProperty("DefaultPrice")
    private Double defaultPrice;

    @Column(nullable = false)
    @JsonProperty("DiscountPrice")
    private Double discountPrice;

    @Column(length = 1000)
    @JsonProperty("Link")
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    @JsonProperty("Category")
    private ProductCategory category;

    @NotNull(message = "Stock is required")
    @JsonProperty("Stock")
    private Integer stock;

    @Column(length = 255)
    @JsonProperty("License")
    private String license;

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    @JsonProperty("ProductType")
    private ProductType productType;

    @Column(length = 2000)
    @JsonProperty("Description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonProperty("Images")
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "wishlistItems")
    private List<Wishlist> wishlists = new ArrayList<>();
}
