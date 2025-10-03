package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.response.OrderResponse;
import com.fahmi.personalonlinestore.entity.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .total(order.getTotal())
                .items(order.getOrderDetails() != null ?
                        order.getOrderDetails().stream()
                                .map(OrderDetailMapper::toResponse)
                                .collect(Collectors.toList()) :
                        null
                )
                .status(order.getStatus())
                .build();
    }
}
