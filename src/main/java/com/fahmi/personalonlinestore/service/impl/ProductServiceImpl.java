package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.entity.Category;
import com.fahmi.personalonlinestore.entity.Product;
import com.fahmi.personalonlinestore.mapper.ProductMapper;
import com.fahmi.personalonlinestore.repository.ProductRepository;
import com.fahmi.personalonlinestore.service.CategoryService;
import com.fahmi.personalonlinestore.service.CloudinaryService;
import com.fahmi.personalonlinestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(String name, String description, BigDecimal price, int stock, String categoryId, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png"))) {
            throw new RuntimeException("Only JPG/PNG files are allowed.");
        }
        String photoUrl = cloudinaryService.uploadFile(file, "products");
        Category category = categoryService.getCategoryById(categoryId);
        ProductRequest request = ProductRequest.builder()
                .name(name)
                .photoUrl(photoUrl)
                .description(description)
                .price(price)
                .stock(stock)
                .category(category)
                .build();

        Product product = ProductMapper.fromRequest(request);

        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Category category = categoryService.getCategoryById(id);
        Product product = productRepository.findById(id).map(p -> {
            p.setName(request.getName());
            p.setDescription(request.getDescription());
            p.setPrice(request.getPrice());
            p.setStock(request.getStock());
            p.setCategory(category);
            return productRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toResponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}

