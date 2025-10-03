package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.OrderRequest;
import com.fahmi.personalonlinestore.dto.response.OrderResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    PagedResponse<OrderResponse> getMyOrders(Pageable pageable);

    void updateOrderStatus(String id, String status);
}

