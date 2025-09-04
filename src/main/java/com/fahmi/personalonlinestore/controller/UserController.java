package com.fahmi.personalonlinestore.controller;

import com.fahmi.personalonlinestore.constant.Endpoint;
import com.fahmi.personalonlinestore.dto.request.OrderRequest;
import com.fahmi.personalonlinestore.dto.response.OrderResponse;
import com.fahmi.personalonlinestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.USER)
@RequiredArgsConstructor
public class UserController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        List<OrderResponse> response = orderService.getMyOrders();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/orders/{id}/add-product")
    public ResponseEntity<OrderResponse> addProductToOrder(
            @PathVariable String id,
            @RequestParam String productId,
            @RequestParam int quantity) {
        OrderResponse response = orderService.addProductToOrder(id, productId, quantity);
        return ResponseEntity.ok(response);
    }
}

