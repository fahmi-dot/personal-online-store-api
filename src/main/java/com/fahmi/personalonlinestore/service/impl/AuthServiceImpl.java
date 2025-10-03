package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.UserLoginRequest;
import com.fahmi.personalonlinestore.dto.request.UserRegisterRequest;
import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.exception.CustomException;
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
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException.ConflictException("Username is already taken.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException.ConflictException("Email already in use.");
        }

        User user = UserMapper.fromRegisterRequest(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public String login(UserLoginRequest request) {
        if (request.getUsernameOrEmail() == null || request.getUsernameOrEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new CustomException.BadRequestException("Username or email and password is required.");
        }
        User user;
        if (request.getUsernameOrEmail().split("@").length != 2) {
            user = userRepository.findByUsername(request.getUsernameOrEmail())
                    .orElseThrow(() -> new CustomException.ResourceNotFoundException("User not found."));
        } else {
            user = userRepository.findByEmail(request.getUsernameOrEmail())
                    .orElseThrow(() -> new CustomException.ResourceNotFoundException("User not found."));
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException.AuthenticationException("Email or password is incorrect.");
        }

        return jwtUtil.generateToken(user);
    }
}
