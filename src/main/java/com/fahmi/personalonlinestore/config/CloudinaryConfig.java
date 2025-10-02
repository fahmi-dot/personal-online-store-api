package com.fahmi.personalonlinestore.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private String cloudName;
    private String apiKey;
    private String apiSecret;

    public CloudinaryConfig() {
        Dotenv dotenv = Dotenv.load();

        this.cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
        this.apiKey = dotenv.get("CLOUDINARY_API_KEY");
        this.apiSecret = dotenv.get("CLOUDINARY_API_SECRET");
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}
