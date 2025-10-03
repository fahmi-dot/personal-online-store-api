package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.response.CartResponse;
import com.fahmi.personalonlinestore.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {
    public static CartResponse toResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .total(cart.getTotal())
                .items(cart.getCartDetails() != null ?
                        cart.getCartDetails().stream()
                                .map(CartDetailMapper::toResponse)
                                .collect(Collectors.toList()) :
                        null
                )
                .build();
    }
}
