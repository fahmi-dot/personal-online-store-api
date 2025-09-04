package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.entity.Product;

import java.util.List;

public interface ProductService {

    public Product createProduct(Product product, String categoryId);

    public List<Product> getAllProducts();

    public Product getProductById(String id);

    public List<Product> getProductsByCategory(String categoryId);

    public Product updateProduct(String id, Product product);

    public void deleteProduct(String id);
}
