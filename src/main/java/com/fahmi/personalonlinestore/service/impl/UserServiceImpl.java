package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.mapper.UserMapper;
import com.fahmi.personalonlinestore.repository.UserRepository;
import com.fahmi.personalonlinestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}

