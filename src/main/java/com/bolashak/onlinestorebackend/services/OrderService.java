package com.bolashak.onlinestorebackend.services;

import com.bolashak.onlinestorebackend.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(String shippingAddress);
    Order getOrderById(Long orderId);
    List<Order> getOrdersForCurrentUser();
    void cancelOrder(Long orderId);
}