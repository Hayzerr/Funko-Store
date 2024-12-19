package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.dto.WishlistDto;
import com.bolashak.onlinestorebackend.entities.Wishlist;

public interface WishlistService {
    Wishlist getWishlistForCurrentUser();
    Wishlist addProductToWishlist(String productId);
    void removeProductFromWishlist(String productId);
    void clearWishlist();
}


