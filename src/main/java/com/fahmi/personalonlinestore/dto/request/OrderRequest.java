package com.fahmi.personalonlinestore.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderDetailRequest> item;
}
