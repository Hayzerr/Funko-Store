package com.bolashak.onlinestorebackend.entities;

import com.bolashak.onlinestorebackend.deserializer.ImageDeserializer;
import com.bolashak.onlinestorebackend.entities.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
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


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

    @JsonIgnore
    @ManyToMany(mappedBy = "wishlistItems")
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonProperty("Images")
    @JsonDeserialize(contentUsing = ImageDeserializer.class)
    private List<Image> images = new ArrayList<>();

    public void setImages(List<Image> images) {
        this.images = images;

        if (images != null) {
            for (Image image : images) {
                image.setProduct(this);
            }
        }
    }
}
