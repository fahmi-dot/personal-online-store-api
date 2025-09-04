package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.OrderDetailRequest;
import com.fahmi.personalonlinestore.dto.request.OrderRequest;
import com.fahmi.personalonlinestore.dto.response.OrderResponse;
import com.fahmi.personalonlinestore.entity.Order;
import com.fahmi.personalonlinestore.entity.OrderDetail;
import com.fahmi.personalonlinestore.entity.Product;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.mapper.OrderMapper;
import com.fahmi.personalonlinestore.repository.OrderDetailRepository;
import com.fahmi.personalonlinestore.repository.OrderRepository;
import com.fahmi.personalonlinestore.repository.ProductRepository;
import com.fahmi.personalonlinestore.repository.UserRepository;
import com.fahmi.personalonlinestore.service.OrderService;
import com.fahmi.personalonlinestore.util.TokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TokenHolder tokenHolder;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        String username = tokenHolder.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .status("PENDING")
                .totalPrice(0.0)
                .build();
        orderRepository.save(order);

        for (OrderDetailRequest item : request.getItem()) {
            addProductToOrder(order.getId(), item.getProductId(), item.getQuantity());
        }

        return OrderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        String username = tokenHolder.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserId(user.getId()).stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse addProductToOrder(String id, String productId, int quantity) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock not sufficient");
        }

        double subtotal = product.getPrice() * quantity;

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .subtotal(subtotal)
                .build();

        orderDetailRepository.save(orderDetail);

        order.setTotalPrice(order.getTotalPrice() + subtotal);
        orderRepository.save(order);

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        return OrderMapper.toResponse(order);
    }

    @Override
    public void updateOrderStatus(String id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        orderRepository.save(order);
    }
}

