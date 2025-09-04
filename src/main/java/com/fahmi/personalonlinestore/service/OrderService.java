package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.OrderRequest;
import com.fahmi.personalonlinestore.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse addProductToOrder(String id, String productId, int quantity);

    void updateOrderStatus(String id, String status);
}

