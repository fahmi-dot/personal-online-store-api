package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.entity.Order;
import com.fahmi.personalonlinestore.entity.OrderDetail;
import com.fahmi.personalonlinestore.entity.Product;
import com.fahmi.personalonlinestore.entity.User;
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

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TokenHolder tokenHolder;

    @Transactional
    public Order createOrder(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = Order.builder()
                .user(user)
                .status("PENDING")
                .totalPrice(0.0)
                .build();
        return orderRepository.save(order);
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getMyOrders() {
        String username = tokenHolder.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUserId(user.getId());
    }

    @Transactional
    public Order addProductToOrder(String id, String productId, int quantity) {
        Order order = getOrderById(id);
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

        return order;
    }

    public void updateOrderStatus(String id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}

