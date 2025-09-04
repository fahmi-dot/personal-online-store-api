package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.UserLoginRequest;
import com.fahmi.personalonlinestore.dto.request.UserRegisterRequest;
import com.fahmi.personalonlinestore.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(UserRegisterRequest request);

    String login(UserLoginRequest request);
}
