package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartDetailResponse {
    private String id;
    private String productId;
    private String variant;
    private String size;
    private int quantity;
    private BigDecimal subtotal;
}
