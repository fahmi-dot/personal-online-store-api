package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.response.UserResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import com.fahmi.personalonlinestore.entity.User;
import org.springframework.data.domain.Pageable;

public interface UserService {
    PagedResponse<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getMyProfile();

    void deleteUser(String id);

    User findUserByUsername(String username);
}
