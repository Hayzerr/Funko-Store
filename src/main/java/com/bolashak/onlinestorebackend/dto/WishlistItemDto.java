package com.bolashak.onlinestorebackend.dto;

import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WishlistItemDto {
    private Long id;
    private String productId;
    private String name;
    private ProductFeature feature;
    private Double defaultPrice;
    private Double discountPrice;
    private String link;
}
