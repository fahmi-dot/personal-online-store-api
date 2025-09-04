package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(String userId);

    Order getOrderById(String id);

    List<Order> getMyOrders();

    Order addProductToOrder(String id, String productId, int quantity);

    void updateOrderStatus(String id, String status);

    void deleteOrder(String id);
}

