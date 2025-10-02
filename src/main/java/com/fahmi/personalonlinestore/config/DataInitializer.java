package com.fahmi.personalonlinestore.config;

import com.fahmi.personalonlinestore.constant.Role;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("Data initializer is running...");
        initUser();
        log.info("Data initializer has finished.");
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
