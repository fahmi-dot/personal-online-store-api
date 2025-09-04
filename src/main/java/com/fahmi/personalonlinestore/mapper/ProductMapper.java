package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.entity.Category;
import com.fahmi.personalonlinestore.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static Product fromRequest(ProductRequest request, Category category) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
