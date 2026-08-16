package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String id;
    private String userId;
    private BigDecimal total;
    private List<OrderDetailResponse> items;
    private String status;
    private LocalDateTime createdAt;
}

