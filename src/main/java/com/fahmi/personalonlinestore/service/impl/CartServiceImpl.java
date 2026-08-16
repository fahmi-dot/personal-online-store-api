package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.CartDetailRequest;
import com.fahmi.personalonlinestore.dto.response.CartResponse;
import com.fahmi.personalonlinestore.entity.*;
import com.fahmi.personalonlinestore.exception.CustomException;
import com.fahmi.personalonlinestore.mapper.CartMapper;
import com.fahmi.personalonlinestore.repository.CartDetailRepository;
import com.fahmi.personalonlinestore.repository.CartRepository;
import com.fahmi.personalonlinestore.service.CartService;
import com.fahmi.personalonlinestore.service.ProductService;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.TokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductService productService;
    private final UserService userService;
    private final TokenHolder tokenHolder;

    @Override
    @Transactional
    public void addProductToCart(CartDetailRequest request) {
        String username = tokenHolder.getUsername();
        if ("admin".equals(username)) {
            throw new CustomException.AuthorizationException("You are the administrator.");
        }
        User user = userService.findUserByUsername(username);
        Cart cart = getOrCreateCart(user);
        Product product = productService.findProductById(request.getProductId());

        if (product.getStock() < request.getQuantity()) {
            throw new CustomException.ConflictException("Requested quantity exceeds product stock.");
        }

        List<CartDetail> currentDetails = cart.getCartDetails();
        if (currentDetails == null) {
            currentDetails = new ArrayList<>();
        }

        // Cek apakah item dengan produk, varian, dan size yang sama sudah ada di keranjang
        Optional<CartDetail> existingItem = currentDetails.stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId())
                        && Objects.equals(item.getVariant(), request.getVariant())
                        && Objects.equals(item.getSize(), request.getSize()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartDetail item = existingItem.get();
            int newQuantity = item.getQuantity() + request.getQuantity();
            if (newQuantity > product.getStock()) {
                throw new CustomException.ConflictException("Total quantity in cart exceeds product stock.");
            }
            item.setQuantity(newQuantity);
            item.setSubtotal(product.getPrice().multiply(new BigDecimal(newQuantity)));
            cartDetailRepository.save(item);
        } else {
            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(request.getQuantity()));
            CartDetail cartDetail = CartDetail.builder()
                    .variant(request.getVariant())
                    .size(request.getSize())
                    .quantity(request.getQuantity())
                    .subtotal(subtotal)
                    .cart(cart)
                    .product(product)
                    .build();
            cartDetailRepository.save(cartDetail);
            currentDetails.add(cartDetail);
        }

        cart.setCartDetails(currentDetails);
        recalculateCartTotal(cart);
    }

    @Override
    @Transactional
    public void updateCartDetail(String detailId, int quantity) {
        String username = tokenHolder.getUsername();
        User user = userService.findUserByUsername(username);
        Cart cart = getOrCreateCart(user);

        CartDetail detail = cartDetailRepository.findById(detailId)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Cart item not found."));

        if (!detail.getCart().getId().equals(cart.getId())) {
            throw new CustomException.AuthorizationException("Unauthorized access to this cart item.");
        }

        if (quantity <= 0) {
            deleteCartDetail(detailId);
            return;
        }

        if (quantity > detail.getProduct().getStock()) {
            throw new CustomException.ConflictException("Requested quantity exceeds product stock.");
        }

        detail.setQuantity(quantity);
        detail.setSubtotal(detail.getProduct().getPrice().multiply(new BigDecimal(quantity)));
        cartDetailRepository.save(detail);

        recalculateCartTotal(cart);
    }

    @Override
    @Transactional
    public void deleteCartDetail(String detailId) {
        String username = tokenHolder.getUsername();
        User user = userService.findUserByUsername(username);
        Cart cart = getOrCreateCart(user);

        CartDetail detail = cartDetailRepository.findById(detailId)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Cart item not found."));

        if (!detail.getCart().getId().equals(cart.getId())) {
            throw new CustomException.AuthorizationException("Unauthorized access to this cart item.");
        }

        cart.getCartDetails().removeIf(d -> d.getId().equals(detailId));
        cartDetailRepository.delete(detail);

        recalculateCartTotal(cart);
    }

    @Override
    @Transactional
    public void clearCart() {
        String username = tokenHolder.getUsername();
        User user = userService.findUserByUsername(username);
        Cart cart = getOrCreateCart(user);

        cartDetailRepository.deleteByCartId(cart.getId());
        if (cart.getCartDetails() != null) {
            cart.getCartDetails().clear();
        }
        cart.setTotal(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public CartResponse getMyCart() {
        String username = tokenHolder.getUsername();
        if ("admin".equals(username)) {
            throw new CustomException.AuthorizationException("You are the administrator.");
        }
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
                            .cartDetails(new ArrayList<>())
                            .build();
                    cartRepository.save(cart);
                    return cart;
                });
    }

    private void recalculateCartTotal(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        if (cart.getCartDetails() != null) {
            for (CartDetail d : cart.getCartDetails()) {
                if (d.getSubtotal() != null) {
                    total = total.add(d.getSubtotal());
                }
            }
        }
        cart.setTotal(total);
        cartRepository.save(cart);
    }
}

