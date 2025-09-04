package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(String id);

    User updateUser(String id, User user);

    void deleteUser(String id);
}
