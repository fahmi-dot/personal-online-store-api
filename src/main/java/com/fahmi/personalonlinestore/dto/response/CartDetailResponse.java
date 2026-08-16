package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartDetailResponse {
    private String id;
    private String productId;
    private String productName;
    private String photoUrl;
    private BigDecimal price;
    private int stock;
    private String variant;
    private String size;
    private int quantity;
    private BigDecimal subtotal;
}

