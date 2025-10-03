package com.fahmi.personalonlinestore.repository;

import com.fahmi.personalonlinestore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findByUserId(Pageable pageable, String id);
}

