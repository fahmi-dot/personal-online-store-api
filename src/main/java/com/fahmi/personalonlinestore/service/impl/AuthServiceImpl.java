package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.constant.Role;
import com.fahmi.personalonlinestore.dto.request.UserLoginRequest;
import com.fahmi.personalonlinestore.dto.request.UserRegisterRequest;
import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.mapper.UserMapper;
import com.fahmi.personalonlinestore.repository.UserRepository;
import com.fahmi.personalonlinestore.service.AuthService;
import com.fahmi.personalonlinestore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponse register(UserRegisterRequest request) {
        User user = UserMapper.fromRegisterRequest(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public String login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user);
    }
}
