package com.fahmi.personalonlinestore.repository;

import com.fahmi.personalonlinestore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
