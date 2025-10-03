package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {
    ProductResponse createProduct(String name, String description, BigDecimal price, int stock, String categoryId, MultipartFile file);

    PagedResponse<ProductResponse> getAllProducts(Pageable pageable, String category, String search);

    ProductResponse getProductById(String id);

    ProductResponse updateProduct(String id, ProductRequest product);

    void deleteProduct(String id);

    Product findProductById(String id);

    Product saveProduct(Product product);
}
