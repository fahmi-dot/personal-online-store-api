package com.fahmi.personalonlinestore.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String email;
    private String password;
}
