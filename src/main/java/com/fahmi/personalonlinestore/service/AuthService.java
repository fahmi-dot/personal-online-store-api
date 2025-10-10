package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.RefreshTokenRequest;
import com.fahmi.personalonlinestore.dto.request.UserLoginRequest;
import com.fahmi.personalonlinestore.dto.request.UserRegisterRequest;
import com.fahmi.personalonlinestore.dto.response.RefreshTokenResponse;
import com.fahmi.personalonlinestore.dto.response.UserLoginResponse;
import com.fahmi.personalonlinestore.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(UserRegisterRequest request);

    UserLoginResponse login(UserLoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
