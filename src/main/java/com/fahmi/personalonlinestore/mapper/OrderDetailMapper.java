package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.response.OrderDetailResponse;
import com.fahmi.personalonlinestore.entity.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {
    public static OrderDetailResponse toResponse(OrderDetail detail) {
        return OrderDetailResponse.builder()
                .id(detail.getId())
                .productId(detail.getProduct().getId())
                .productName(detail.getProduct().getName())
                .photoUrl(detail.getProduct().getPhotoUrl())
                .price(detail.getProduct().getPrice())
                .quantity(detail.getQuantity())
                .subtotal(detail.getSubtotal())
                .build();
    }
}
