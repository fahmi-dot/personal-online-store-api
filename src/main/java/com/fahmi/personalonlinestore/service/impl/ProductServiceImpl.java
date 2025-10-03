package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.ProductRequest;
import com.fahmi.personalonlinestore.dto.response.ProductResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.entity.Category;
import com.fahmi.personalonlinestore.entity.Product;
import com.fahmi.personalonlinestore.exception.CustomException;
import com.fahmi.personalonlinestore.mapper.ProductMapper;
import com.fahmi.personalonlinestore.repository.ProductRepository;
import com.fahmi.personalonlinestore.service.CategoryService;
import com.fahmi.personalonlinestore.service.CloudinaryService;
import com.fahmi.personalonlinestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;

    @Override
    public ProductResponse createProduct(String name, String description, BigDecimal price, int stock, String categoryId, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png"))) {
            throw new CustomException.BadRequestException("Only JPG/PNG files are allowed.");
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
    public PagedResponse<ProductResponse> getAllProducts(Pageable pageable, String category, String search) {
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = products.stream()
                .map(ProductMapper::toResponse)
                .toList();

        return toPagedResponse(products, productResponses);
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = findProduct(id);

        return ProductMapper.toResponse(product);
    }

    @Override
    public Product findProductById(String id) {
        return findProduct(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Category category = categoryService.getCategoryById(id);
        Product product = findProduct(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);
        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    public Product findProduct(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Product not found."));
    }

    public PagedResponse<ProductResponse> toPagedResponse(Page<Product> products, List<ProductResponse> productResponses) {
        return PagedResponse.<ProductResponse>builder()
                .data(productResponses)
                .page(products.getNumber())
                .size(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .build();
    }
}

