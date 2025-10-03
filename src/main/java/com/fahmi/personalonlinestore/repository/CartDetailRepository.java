package com.fahmi.personalonlinestore.repository;

import com.fahmi.personalonlinestore.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, String> {
}
