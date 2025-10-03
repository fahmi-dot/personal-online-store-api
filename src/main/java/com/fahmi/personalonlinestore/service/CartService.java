package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.CartDetailRequest;
import com.fahmi.personalonlinestore.dto.response.CartResponse;

public interface CartService {
    void addProductToCart(CartDetailRequest request);

    CartResponse getMyCart();
}
