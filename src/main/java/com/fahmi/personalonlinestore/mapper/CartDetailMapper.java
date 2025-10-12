package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.response.CartDetailResponse;
import com.fahmi.personalonlinestore.entity.CartDetail;
import org.springframework.stereotype.Component;

@Component
public class CartDetailMapper {
    public static CartDetailResponse toResponse(CartDetail detail) {
        return CartDetailResponse.builder()
                .id(detail.getId())
                .productId(detail.getProduct().getId())
                .variant(detail.getVariant())
                .size(detail.getSize())
                .quantity(detail.getQuantity())
                .subtotal(detail.getSubtotal())
                .build();
    }
}
