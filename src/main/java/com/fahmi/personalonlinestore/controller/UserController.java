package com.fahmi.personalonlinestore.controller;

import com.fahmi.personalonlinestore.constant.Endpoint;
import com.fahmi.personalonlinestore.entity.Order;
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
    public ResponseEntity<List<Order>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestParam String userId) {
        return ResponseEntity.ok(orderService.createOrder(userId));
    }

    @PostMapping("/orders/{id}/add-product")
    public ResponseEntity<Order> addProductToOrder(
            @PathVariable String id,
            @RequestParam String productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(orderService.addProductToOrder(id, productId, quantity));
    }
}

