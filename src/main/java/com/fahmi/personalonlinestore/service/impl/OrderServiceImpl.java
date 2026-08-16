package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.OrderDetailRequest;
import com.fahmi.personalonlinestore.dto.request.OrderRequest;
import com.fahmi.personalonlinestore.dto.response.OrderResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse.WithData;
import com.fahmi.personalonlinestore.entity.Order;
import com.fahmi.personalonlinestore.entity.OrderDetail;
import com.fahmi.personalonlinestore.entity.Product;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.exception.CustomException;
import com.fahmi.personalonlinestore.mapper.OrderMapper;
import com.fahmi.personalonlinestore.repository.OrderDetailRepository;
import com.fahmi.personalonlinestore.repository.OrderRepository;
import com.fahmi.personalonlinestore.service.CartService;
import com.fahmi.personalonlinestore.service.OrderService;
import com.fahmi.personalonlinestore.service.ProductService;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.TokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final TokenHolder tokenHolder;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        String username = tokenHolder.getUsername();
        if ("admin".equals(username)) {
            throw new CustomException.AuthorizationException("You are the administrator.");
        }
        if (request.getItem() == null || request.getItem().isEmpty()) {
            throw new CustomException.BadRequestException("Order must contain at least one item.");
        }
        User user = userService.findUserByUsername(username);
        Order order = Order.builder()
                .user(user)
                .status("PENDING")
                .total(new BigDecimal("0.0"))
                .createdAt(java.time.LocalDateTime.now())
                .build();
        orderRepository.save(order);
        for (OrderDetailRequest item : request.getItem()) {
            addProductToOrder(order.getId(), item.getProductId(), item.getQuantity());
        }

        // Kosongkan keranjang setelah order berhasil dibuat
        try {
            cartService.clearCart();
        } catch (Exception e) {
            // Ignore if cart is already empty
        }

        return OrderMapper.toResponse(order);
    }

    @Override
    public PagedResponse.WithData<OrderResponse> getMyOrders(Pageable pageable) {
        String username = tokenHolder.getUsername();
        if ("admin".equals(username)) {
            throw new CustomException.AuthorizationException("You are the administrator.");
        }
        User user = userService.findUserByUsername(username);
        Page<Order> orders =  orderRepository.findByUserId(pageable, user.getId());
        List<OrderResponse> orderResponses = orders.stream()
                .map(OrderMapper::toResponse)
                .toList();
        PagedResponse<OrderResponse> pagedResponse = toPagedResponse(orders);

        return PagedResponse.WithData.<OrderResponse>builder()
                .data(orderResponses)
                .pagination(pagedResponse)
                .build();
    }

    @Override
    public PagedResponse.WithData<OrderResponse> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        List<OrderResponse> orderResponses = orders.stream()
                .map(OrderMapper::toResponse)
                .toList();
        PagedResponse<OrderResponse> pagedResponse = toPagedResponse(orders);

        return PagedResponse.WithData.<OrderResponse>builder()
                .data(orderResponses)
                .pagination(pagedResponse)
                .build();
    }

    public void addProductToOrder(String id, String productId, int quantity) {
        Order order = findOrder(id);
        Product product = productService.findProductById(productId);
        if (product.getStock() < quantity) {
            throw new CustomException.ConflictException("Quantity exceeds stock.");
        }
        BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(quantity));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .subtotal(subtotal)
                .build();
        orderDetailRepository.save(orderDetail);

        order.setTotal(order.getTotal().add(subtotal));
        orderRepository.save(order);

        product.setStock(product.getStock() - quantity);
        productService.saveProduct(product);

        OrderMapper.toResponse(order);
    }

    @Override
    public void updateOrderStatus(String id, String status) {
        Order order = findOrder(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(String id) {
        String username = tokenHolder.getUsername();
        User user = userService.findUserByUsername(username);
        Order order = findOrder(id);

        if (!order.getUser().getId().equals(user.getId())) {
            throw new CustomException.AuthorizationException("You are not authorized to cancel this order.");
        }

        if (!"PENDING".equalsIgnoreCase(order.getStatus())) {
            throw new CustomException.ConflictException("Only orders with PENDING status can be cancelled.");
        }

        // Kembalikan stok produk
        if (order.getOrderDetails() != null) {
            for (OrderDetail detail : order.getOrderDetails()) {
                Product product = detail.getProduct();
                if (product != null) {
                    product.setStock(product.getStock() + detail.getQuantity());
                    productService.saveProduct(product);
                }
            }
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    public Order findOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Order not found."));
    }

    public PagedResponse<OrderResponse> toPagedResponse(Page<Order> orders) {
        return PagedResponse.<OrderResponse>builder()
                .currentPage(orders.getNumber())
                .pageSize(orders.getSize())
                .totalItems(orders.getTotalElements())
                .totalPages(orders.getTotalPages())
                .hasPrev(orders.hasPrevious())
                .hasNext(orders.hasNext())
                .build();
    }

    @Override
    public WithData<OrderResponse> getAllOrders(Pageable pageable) {
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }
}

