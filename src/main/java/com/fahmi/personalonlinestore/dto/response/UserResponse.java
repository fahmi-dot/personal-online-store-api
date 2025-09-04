package com.fahmi.personalonlinestore.dto.response;

import com.fahmi.personalonlinestore.constant.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Role role;
}

