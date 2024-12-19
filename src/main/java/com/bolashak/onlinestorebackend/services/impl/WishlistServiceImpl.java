package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.dto.WishlistDto;
import com.bolashak.onlinestorebackend.dto.WishlistItemDto;
import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.entities.User;
import com.bolashak.onlinestorebackend.entities.Wishlist;
import com.bolashak.onlinestorebackend.repository.ProductRepository;
import com.bolashak.onlinestorebackend.repository.UserRepository;
import com.bolashak.onlinestorebackend.repository.WishlistRepository;
import com.bolashak.onlinestorebackend.services.WishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Wishlist getWishlistForCurrentUser() {
        User currentUser = getCurrentUser();
        return wishlistRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> createWishlistForUser(currentUser));
    }

    @Override
    @Transactional
    public Wishlist addProductToWishlist(String productId) {
        Wishlist wishlist = getWishlistForCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (!wishlist.getWishlistItems().contains(product)) {
            wishlist.getWishlistItems().add(product);
        }
        return wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void removeProductFromWishlist(String productId) {
        Wishlist wishlist = getWishlistForCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        wishlist.getWishlistItems().remove(product);
        wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void clearWishlist() {
        Wishlist wishlist = getWishlistForCurrentUser();
        wishlist.getWishlistItems().clear();
        wishlistRepository.save(wishlist);
    }

    private Wishlist createWishlistForUser(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setWishlistItems(new ArrayList<>());
        return wishlistRepository.save(wishlist);
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
