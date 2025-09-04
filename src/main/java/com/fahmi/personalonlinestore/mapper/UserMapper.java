package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.constant.Role;
import com.fahmi.personalonlinestore.dto.request.UserRegisterRequest;
import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User fromRegisterRequest(UserRegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
