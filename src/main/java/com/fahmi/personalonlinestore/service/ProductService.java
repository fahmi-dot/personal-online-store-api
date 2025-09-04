package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest product);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String id);

    ProductResponse updateProduct(String id, ProductRequest product);

    void deleteProduct(String id);
}
