package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartResponse {
    private String id;
    private String userId;
    private BigDecimal total;
    private List<CartDetailResponse> items;
}
