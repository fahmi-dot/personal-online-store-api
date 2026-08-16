package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.OrderRequest;
import com.fahmi.personalonlinestore.dto.response.OrderResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    PagedResponse.WithData<OrderResponse> getMyOrders(Pageable pageable);

    PagedResponse.WithData<OrderResponse> getAllOrders(Pageable pageable);

    void updateOrderStatus(String id, String status);

    void cancelOrder(String id);
}

