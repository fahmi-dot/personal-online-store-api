package com.fahmi.personalonlinestore.dto.request;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private String productId;
    private int quantity;
}
