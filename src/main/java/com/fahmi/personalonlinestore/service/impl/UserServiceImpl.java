package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.exception.CustomException;
import com.fahmi.personalonlinestore.mapper.UserMapper;
import com.fahmi.personalonlinestore.repository.UserRepository;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.TokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenHolder tokenHolder;

    @Override
    public PagedResponse.WithData<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> userResponses = users.stream()
                .map(UserMapper::toResponse)
                .toList();
        PagedResponse<UserResponse> pagedResponse = toPagedResponse(users);

        return PagedResponse.WithData.<UserResponse>builder()
                .data(userResponses)
                .pagination(pagedResponse)
                .build();
    }

    @Override
    public UserResponse getMyProfile() {
        String username = tokenHolder.getUsername();
        User user = findUserByUsername(username);

        return UserMapper.toResponse(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("User not found."));
    }

    public User findUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("User not found."));
    }

    public PagedResponse<UserResponse> toPagedResponse(Page<User> users) {
        return PagedResponse.<UserResponse>builder()
                .currentPage(users.getNumber())
                .pageSize(users.getSize())
                .totalItems(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .hasPrev(users.hasPrevious())
                .hasNext(users.hasNext())
                .build();
    }
}

