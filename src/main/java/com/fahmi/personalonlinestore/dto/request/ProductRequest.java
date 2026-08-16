package com.fahmi.personalonlinestore.dto.request;

import com.fahmi.personalonlinestore.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;
    private String photoUrl;
    private String description;
    private BigDecimal price;
    private int stock;
    private String categoryId;
    private Category category;
}
