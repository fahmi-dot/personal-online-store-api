package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.CartDetailRequest;
import com.fahmi.personalonlinestore.dto.response.CartResponse;
import com.fahmi.personalonlinestore.entity.*;
import com.fahmi.personalonlinestore.mapper.CartMapper;
import com.fahmi.personalonlinestore.repository.CartDetailRepository;
import com.fahmi.personalonlinestore.repository.CartRepository;
import com.fahmi.personalonlinestore.service.CartService;
import com.fahmi.personalonlinestore.service.ProductService;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.TokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductService productService;
    private final UserService userService;
    private final TokenHolder tokenHolder;

    @Override
    public void addProductToCart(CartDetailRequest request) {
        String username = tokenHolder.getUsername();
        User user = userService.findUserByUsername(username);
        Cart cart = getOrCreateCart(user);
        Product product = productService.findProductById(request.getProductId());
        BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(request.getQuantity()));
        CartDetail cartDetail = CartDetail.builder()
                .quantity(request.getQuantity())
                .subtotal(subtotal)
                .cart(cart)
                .product(product)
                .build();
        cartDetailRepository.save(cartDetail);

        List<CartDetail> currentCartDetail = cart.getCartDetails();
        currentCartDetail.add(cartDetail);
        cart.setCartDetails(currentCartDetail);
        cart.setTotal(cart.getTotal().add(subtotal));
        cartRepository.save(cart);
    }

    @Override
    public CartResponse getMyCart() {
        String username = tokenHolder.getUsername();
        User user = userService.findUserByUsername(username);
        Cart cart = getOrCreateCart(user);

        return CartMapper.toResponse(cart);
    }

    public Cart getOrCreateCart(User user) {
        return cartRepository.findById(user.getId())
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .id(user.getId())
                            .total(new BigDecimal("0.0"))
                            .user(user)
                            .cartDetails(null)
                            .build();
                    cartRepository.save(cart);
                    return cart;
                });
    }
}
