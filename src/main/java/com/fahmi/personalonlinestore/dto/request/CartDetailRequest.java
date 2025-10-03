package com.fahmi.personalonlinestore.dto.request;

import lombok.Data;

@Data
public class CartDetailRequest {
    private String productId;
    private int quantity;
}
