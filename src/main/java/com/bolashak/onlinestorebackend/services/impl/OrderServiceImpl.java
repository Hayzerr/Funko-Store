package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.entities.*;
import com.bolashak.onlinestorebackend.repository.CartRepository;
import com.bolashak.onlinestorebackend.repository.OrderRepository;
import com.bolashak.onlinestorebackend.repository.ProductRepository;
import com.bolashak.onlinestorebackend.repository.UserRepository;
import com.bolashak.onlinestorebackend.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Order createOrder(String shippingAddress) {
        User currentUser = getCurrentUser();

        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + currentUser.getId()));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot create order.");
        }

        Order order = new Order();
        order.setUser(currentUser);
        order.setShippingAddress(shippingAddress);
        order.setStatus("PENDING");
        order.setTotalAmount(0.0);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getDefaultPrice() * cartItem.getQuantity());

            totalAmount += orderItem.getPrice();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        User currentUser = getCurrentUser();
        return orderRepository.findByUserId(currentUser.getId());
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only pending orders can be canceled.");
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() + orderItem.getQuantity());
            productRepository.save(product);
        }

        order.setStatus("CANCELED");
        orderRepository.save(order);
    }

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
