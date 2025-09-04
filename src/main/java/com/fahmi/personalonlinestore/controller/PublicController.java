package com.fahmi.personalonlinestore.controller;

import com.fahmi.personalonlinestore.constant.Endpoint;
import com.fahmi.personalonlinestore.dto.response.CategoryResponse;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.service.CategoryService;
import com.fahmi.personalonlinestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.PUBLIC)
@RequiredArgsConstructor
public class PublicController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }
}
