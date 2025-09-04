package com.fahmi.personalonlinestore.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {

    private String secretKey;
    private long expirationMs;

    public JwtConfig() {
        Dotenv dotenv = Dotenv.load();

        this.secretKey = dotenv.get("JWT_SECRET_KEY");
        this.expirationMs = Long.parseLong(dotenv.get("JWT_EXPIRATION_MS"));
    }
}

