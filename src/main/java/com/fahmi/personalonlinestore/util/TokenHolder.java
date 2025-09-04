package com.fahmi.personalonlinestore.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenHolder {
    private final HttpServletRequest request;
    private final JwtUtil jwtUtil;

    public String getUsername() {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        return jwtUtil.extractUsername(token);
    }
}
