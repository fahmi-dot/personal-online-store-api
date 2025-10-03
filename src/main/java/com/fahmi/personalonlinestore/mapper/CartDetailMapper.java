package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.response.CartDetailResponse;
import com.fahmi.personalonlinestore.entity.CartDetail;
import org.springframework.stereotype.Component;

@Component
public class CartDetailMapper {
    public static CartDetailResponse toResponse(CartDetail detail) {
        return CartDetailResponse.builder()
                .productId(detail.getProduct().getId())
                .quantity(detail.getQuantity())
                .subtotal(detail.getSubtotal())
                .build();
    }
}
