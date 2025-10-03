package com.fahmi.personalonlinestore.controller;

import com.fahmi.personalonlinestore.constant.Endpoint;
import com.fahmi.personalonlinestore.dto.request.CartDetailRequest;
import com.fahmi.personalonlinestore.dto.request.OrderRequest;
import com.fahmi.personalonlinestore.dto.response.CartResponse;
import com.fahmi.personalonlinestore.dto.response.OrderResponse;
import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.service.CartService;
import com.fahmi.personalonlinestore.service.OrderService;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.USER)
@RequiredArgsConstructor
public class UserController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile() {
        UserResponse response = userService.getMyProfile();

        return ResponseUtil.response(
                HttpStatus.OK,
                "My profile retrieved successfully.",
                response
        );
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getMyOrders(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                         @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        PagedResponse<OrderResponse> responses = orderService.getMyOrders(pageable);

        return ResponseUtil.response(
                HttpStatus.OK,
                "All my orders retrieved successfully.",
                responses
        );
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);

        return ResponseUtil.response(
                HttpStatus.CREATED,
                "Order created successfully.",
                response
        );
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getMyCart() {
        CartResponse response = cartService.getMyCart();

        return ResponseUtil.response(
                HttpStatus.OK,
                "My cart retrieved successfully.",
                response
        );
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartDetailRequest request) {
        cartService.addProductToCart(request);

        return ResponseUtil.response(
                HttpStatus.OK,
                "Product added to cart successfully.",
                null
        );
    }
}

