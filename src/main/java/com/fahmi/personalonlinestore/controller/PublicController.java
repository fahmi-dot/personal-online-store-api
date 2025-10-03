package com.fahmi.personalonlinestore.controller;

import com.fahmi.personalonlinestore.constant.Endpoint;
import com.fahmi.personalonlinestore.dto.response.CategoryResponse;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.service.CategoryService;
import com.fahmi.personalonlinestore.service.ProductService;
import com.fahmi.personalonlinestore.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size,
                                            @RequestParam(required = false, defaultValue = "name") String sortBy,
                                            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                            @RequestParam(required = false) String category,
                                            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        PagedResponse<ProductResponse> responses = productService.getAllProducts(pageable, category, search);

        return ResponseUtil.response(
                HttpStatus.OK,
                "All products retrieved successfully.",
                responses
        );
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        ProductResponse response = productService.getProductById(id);

        return ResponseUtil.response(
                HttpStatus.OK,
                "Product retrieved successfully.",
                response
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        List<CategoryResponse> responses = categoryService.getAllCategories();

        return ResponseUtil.response(
                HttpStatus.OK,
                "All categories retrieved successfully.",
                responses
        );
    }
}
