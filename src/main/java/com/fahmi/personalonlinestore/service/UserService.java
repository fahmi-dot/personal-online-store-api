package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    void deleteUser(String id);
}
