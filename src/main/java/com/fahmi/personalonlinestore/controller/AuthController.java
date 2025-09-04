package com.fahmi.personalonlinestore.controller;

import com.fahmi.personalonlinestore.constant.Endpoint;
import com.fahmi.personalonlinestore.dto.request.UserLoginRequest;
import com.fahmi.personalonlinestore.dto.request.UserRegisterRequest;
import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
