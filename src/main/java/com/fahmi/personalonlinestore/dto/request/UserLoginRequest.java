package com.fahmi.personalonlinestore.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String usernameOrEmail;
    private String password;
}
