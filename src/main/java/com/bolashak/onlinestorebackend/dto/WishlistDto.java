package com.bolashak.onlinestorebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WishlistDto {
    private Long id;
    private Long userId;
    private List<WishlistItemDto> wishlistItems;
}

