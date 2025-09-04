package com.fahmi.personalonlinestore.repository;

import com.fahmi.personalonlinestore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByCategoryId(String categoryId);
}
