package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.entities.Cart;

public interface CartService {
    Cart getCartForCurrentUser();
    Cart addProductToCart(String productId, int quantity);
    Cart updateProductQuantity(String productId, int quantity);
    void removeProductFromCart(String productId);
    void clearCart();
}