package com.dev.api_kls.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
