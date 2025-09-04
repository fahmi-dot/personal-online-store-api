package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String id;
    private String userId;
    private double totalPrice;
    private String status;
    private List<OrderDetailResponse> orderDetails;
}

