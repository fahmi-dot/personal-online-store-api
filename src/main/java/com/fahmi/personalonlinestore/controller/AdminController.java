package com.fahmi.personalonlinestore.controller;

import com.fahmi.personalonlinestore.constant.Endpoint;
import com.fahmi.personalonlinestore.dto.request.CategoryRequest;
import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.CategoryResponse;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.service.CategoryService;
import com.fahmi.personalonlinestore.service.OrderService;
import com.fahmi.personalonlinestore.service.ProductService;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping(Endpoint.ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                         @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        PagedResponse<UserResponse> responses = userService.getAllUsers(pageable);

        return ResponseUtil.response(
                HttpStatus.OK,
                "All users retrieved successfully.",
                responses
        );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);

        return ResponseUtil.response(
                HttpStatus.OK,
                "User deleted successfully.",
                null
        );
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);

        return ResponseUtil.response(
                HttpStatus.CREATED,
                "Category created successfully.",
                response
        );
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);

        return ResponseUtil.response(
                HttpStatus.OK,
                "Category updated successfully.",
                response
        );
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);

        return ResponseUtil.response(
                HttpStatus.OK,
                "Category deleted successfully.",
                null
        );
    }

    @PostMapping(value = "/products", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createProduct(@RequestParam String name,
                                                         @RequestParam String description,
                                                         @RequestParam BigDecimal price,
                                                         @RequestParam int stock,
                                                         @RequestParam String categoryId,
                                                         @RequestPart("file") MultipartFile file) {
        ProductResponse response = productService.createProduct(name, description, price, stock, categoryId, file);

        return ResponseUtil.response(
                HttpStatus.CREATED,
                "Product created successfully.",
                response
        );
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);

        return ResponseUtil.response(
                HttpStatus.OK,
                "Product updated successfully.",
                response
        );
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);

        return ResponseUtil.response(
                HttpStatus.OK,
                "Product deleted successfully.",
                null
        );
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String id,
            @RequestParam String status) {
        orderService.updateOrderStatus(id, status);

        return ResponseUtil.response(
                HttpStatus.OK,
                "Order status updated successfully.",
                null
        );
    }
}
