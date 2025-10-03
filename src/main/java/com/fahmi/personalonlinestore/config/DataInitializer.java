package com.fahmi.personalonlinestore.config;

import com.fahmi.personalonlinestore.constant.Role;
import com.fahmi.personalonlinestore.entity.Category;
import com.fahmi.personalonlinestore.entity.Product;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.repository.CategoryRepository;
import com.fahmi.personalonlinestore.repository.ProductRepository;
import com.fahmi.personalonlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        log.info("Data initializer is running...");
        initUser();
        initCategoriesAndProducts();
        log.info("Data initializer has finished.");
    }

    private void initCategoriesAndProducts() {
        try {
            if (categoryRepository.count() == 0) {
                log.info("Initializing categories and products...");

                List<Category> categories = new ArrayList<>();
                categories.add(Category.builder().name("Electronics").build());
                categories.add(Category.builder().name("Books").build());
                categories.add(Category.builder().name("Clothing").build());
                categories.add(Category.builder().name("Home & Kitchen").build());
                categories.add(Category.builder().name("Sports & Outdoors").build());
                categoryRepository.saveAll(categories);

                List<Product> products = new ArrayList<>();
                products.add(createProduct("Laptop", "High-performance laptop", new BigDecimal("1200.00"), 50, categories.get(0)));
                products.add(createProduct("Smartphone", "Latest model smartphone", new BigDecimal("800.00"), 100, categories.get(0)));
                products.add(createProduct("Headphones", "Noise-cancelling headphones", new BigDecimal("250.00"), 200, categories.get(0)));

                products.add(createProduct("The Lord of the Rings", "Fantasy classic by J.R.R. Tolkien", new BigDecimal("25.00"), 150, categories.get(1)));
                products.add(createProduct("Sapiens: A Brief History of Humankind", "By Yuval Noah Harari", new BigDecimal("20.00"), 120, categories.get(1)));
                products.add(createProduct("Atomic Habits", "By James Clear", new BigDecimal("15.00"), 300, categories.get(1)));

                products.add(createProduct("T-Shirt", "Cotton t-shirt", new BigDecimal("30.00"), 500, categories.get(2)));
                products.add(createProduct("Jeans", "Denim jeans", new BigDecimal("75.00"), 300, categories.get(2)));
                products.add(createProduct("Jacket", "Waterproof jacket", new BigDecimal("150.00"), 100, categories.get(2)));

                products.add(createProduct("Coffee Maker", "Drip coffee maker", new BigDecimal("50.00"), 80, categories.get(3)));
                products.add(createProduct("Blender", "High-speed blender", new BigDecimal("120.00"), 60, categories.get(3)));
                products.add(createProduct("Cookware Set", "10-piece cookware set", new BigDecimal("200.00"), 40, categories.get(3)));

                products.add(createProduct("Yoga Mat", "Eco-friendly yoga mat", new BigDecimal("40.00"), 250, categories.get(4)));
                products.add(createProduct("Dumbbell Set", "Adjustable dumbbell set", new BigDecimal("180.00"), 70, categories.get(4)));
                products.add(createProduct("Running Shoes", "Lightweight running shoes", new BigDecimal("110.00"), 400, categories.get(4)));

                productRepository.saveAll(products);
                log.info("Categories and products initialization process finished.");
            }
        } catch (Exception e) {
            log.error("Error while initializing categories and products", e);
        }
    }

    private Product createProduct(String name, String description, BigDecimal price, int stock, Category category) {
        return Product.builder()
            .name(name)
            .description(description)
            .price(price)
            .stock(stock)
            .category(category)
            .photoUrl("https://via.placeholder.com/150")
            .build();
    }

    private void initUser() {
        try {
            log.info("Initializing admin...");
            String adminUsername = "admin";
            String adminEmail = "admin@postore.com";
            Role adminRole = Role.ADMIN;
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = User.builder()
                        .username(adminUsername)
                        .email(adminEmail)
                        .password(passwordEncoder.encode("adminpass"))
                        .role(adminRole)
                        .build();
                userRepository.save(admin);
            }
            log.info("Admin initialization process finished.");

            log.info("Initializing customer...");
            String custUsername = "customer";
            String custEmail = "customer@postore.com";
            Role custRole = Role.USER;

            if (userRepository.findByEmail(custEmail).isEmpty()) {
                User admin = User.builder()
                        .username(custUsername)
                        .email(custEmail)
                        .password(passwordEncoder.encode("customerpass"))
                        .role(custRole)
                        .build();
                userRepository.save(admin);
            }
            log.info("Customer initialization process finished.");
        } catch (Exception e) {
            log.error("Error while initializing user", e);
        }
    }
}
