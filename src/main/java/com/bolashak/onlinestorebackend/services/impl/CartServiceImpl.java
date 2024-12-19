package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.entities.Cart;
import com.bolashak.onlinestorebackend.entities.CartItem;
import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.entities.User;
import com.bolashak.onlinestorebackend.repository.CartRepository;
import com.bolashak.onlinestorebackend.repository.ProductRepository;
import com.bolashak.onlinestorebackend.repository.UserRepository;
import com.bolashak.onlinestorebackend.services.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Cart getCartForCurrentUser() {
        User currentUser = getCurrentUser();
        return cartRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> createCartForUser(currentUser));
    }

    @Override
    @Transactional
    public Cart addProductToCart(String productId, int quantity) {
        Cart cart = getCartForCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        }

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart updateProductQuantity(String productId, int quantity) {
        Cart cart = getCartForCurrentUser();
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (quantity <= 0) {
            cart.getCartItems().remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeProductFromCart(String productId) {
        Cart cart = getCartForCurrentUser();
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart() {
        Cart cart = getCartForCurrentUser();
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>());
        return cartRepository.save(cart);
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
