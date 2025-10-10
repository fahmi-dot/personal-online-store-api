package com.fahmi.personalonlinestore.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {

    private String secretKey;
    private long accessExpirationMs;
    private long refreshExpirationMs;

    public JwtConfig() {
        Dotenv dotenv = Dotenv.load();

        this.secretKey = dotenv.get("JWT_SECRET_KEY");
        this.accessExpirationMs = Long.parseLong(dotenv.get("JWT_ACCESS_EXPIRATION_MS"));
        this.refreshExpirationMs = Long.parseLong(dotenv.get("JWT_REFRESH_EXPIRATION_MS"));
    }
}

