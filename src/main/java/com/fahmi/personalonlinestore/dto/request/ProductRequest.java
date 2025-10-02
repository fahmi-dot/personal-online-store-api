package com.fahmi.personalonlinestore.dto.request;

import com.fahmi.personalonlinestore.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {
    private String name;
    private String photoUrl;
    private String description;
    private BigDecimal price;
    private int stock;
    private Category category;
}
