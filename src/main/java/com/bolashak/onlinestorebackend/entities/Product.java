package com.bolashak.onlinestorebackend.entities;

import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product extends AbstractEntity<Long> {

    @Column(length = 1000, nullable = false)
    @NotNull(message = "Name is required")
    @Size(max = 1000, message = "Name length must be less than or equal to 1000 characters")
    @JsonProperty("Name")
    private String name;

    @Column(length = 500)
    @Size(max = 500, message = "Feature length must be less than or equal to 500 characters")
    @JsonProperty("Feature")
    private String feature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status is required")
    @JsonProperty("Status")
    private ProductStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Default price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Default price must be greater than 0")
    @JsonProperty("DefaultPrice")
    private BigDecimal defaultPrice;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "Discount price must be greater than or equal to 0")
    @JsonProperty("DiscountPrice")
    private BigDecimal discountPrice;

    @Column(length = 1000)
    @JsonProperty("Link")
    private String link;

    @Column(length = 255)
    @JsonProperty("Category")
    private String category;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be 0 or greater")
    @JsonProperty("Stock")
    private Integer stock;

    @Column(length = 255)
    @JsonProperty("License")
    private String license;

    @Column(length = 255)
    @JsonProperty("ProductType")
    private String productType;

    @Column(length = 2000)
    @Size(max = 2000, message = "Description length must be less than or equal to 2000 characters")
    @JsonProperty("Description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonProperty("Images")
    private List<Image> images = new ArrayList<>();
}
