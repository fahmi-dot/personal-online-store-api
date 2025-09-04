package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailResponse {
    private String id;
    private String productId;
    private int quantity;
    private double price;
}

