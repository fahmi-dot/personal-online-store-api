package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.RefreshTokenRequest;
import com.fahmi.personalonlinestore.dto.request.UserLoginRequest;
import com.fahmi.personalonlinestore.dto.request.UserRegisterRequest;
import com.fahmi.personalonlinestore.dto.response.RefreshTokenResponse;
import com.fahmi.personalonlinestore.dto.response.UserLoginResponse;
import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.exception.CustomException;
import com.fahmi.personalonlinestore.mapper.UserMapper;
import com.fahmi.personalonlinestore.repository.UserRepository;
import com.fahmi.personalonlinestore.service.AuthService;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
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
    public UserLoginResponse login(UserLoginRequest request) {
        if (request.getEmailOrUsername() == null || request.getEmailOrUsername().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new CustomException.BadRequestException("Username or email and password is required.");
        }
        User user;
        if (request.getEmailOrUsername().split("@").length != 2) {
            user = userRepository.findByUsername(request.getEmailOrUsername())
                    .orElseThrow(() -> new CustomException.ResourceNotFoundException("User not found."));
        } else {
            user = userRepository.findByEmail(request.getEmailOrUsername())
                    .orElseThrow(() -> new CustomException.ResourceNotFoundException("User not found."));
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException.AuthenticationException("Email or password is incorrect.");
        }
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String username = jwtUtil.extractUsername(request.getRefreshToken());
        User user = userService.findUserByUsername(username);
        String accessToken = jwtUtil.generateAccessToken(user);

        return RefreshTokenResponse.builder().accessToken(accessToken).build();
    }
}
