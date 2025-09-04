package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.entity.User;

public interface AuthService {
    void register(User user);

    String login(String email, String password);
}
