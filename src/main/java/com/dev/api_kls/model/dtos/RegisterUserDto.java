package com.dev.api_kls.model.dtos;

import com.dev.api_kls.model.Role;
import lombok.Data;

@Data
public class RegisterUserDto {

    private String email;
    private String name;
    private String username;
    private String password;
    private Role role;
}
