package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static Product fromRequest(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .photoUrl(request.getPhotoUrl())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .photoUrl(product.getPhotoUrl())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
