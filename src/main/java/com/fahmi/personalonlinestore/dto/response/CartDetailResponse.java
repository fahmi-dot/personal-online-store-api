package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartDetailResponse {
    private String productId;
    private int quantity;
    private BigDecimal subtotal;
}
