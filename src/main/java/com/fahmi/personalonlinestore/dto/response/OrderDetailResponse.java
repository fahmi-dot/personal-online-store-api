package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderDetailResponse {
    private String id;
    private String productId;
    private String productName;
    private String photoUrl;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;
}

