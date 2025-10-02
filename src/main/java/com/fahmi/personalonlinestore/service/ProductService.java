package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(String name, String description, BigDecimal price, int stock, String categoryId, MultipartFile file);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String id);

    ProductResponse updateProduct(String id, ProductRequest product);

    void deleteProduct(String id);
}
