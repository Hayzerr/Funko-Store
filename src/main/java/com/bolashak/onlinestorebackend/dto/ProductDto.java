package com.bolashak.onlinestorebackend.dto;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String feature;
    private ProductStatus status;
    private BigDecimal defaultPrice;
    private BigDecimal discountPrice;
    private String link;
    private String category;
    private Integer stock;
    private String license;
    private String productType;
    private String description;
    private List<String> images;
}