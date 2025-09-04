package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String categoryId;
}

