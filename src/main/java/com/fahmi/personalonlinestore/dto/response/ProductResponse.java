package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String photoUrl;
    private String description;
    private BigDecimal price;
    private int stock;
    private String categoryId;
}

