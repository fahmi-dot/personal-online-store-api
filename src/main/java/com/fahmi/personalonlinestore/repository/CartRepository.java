package com.fahmi.personalonlinestore.repository;

import com.fahmi.personalonlinestore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
}
