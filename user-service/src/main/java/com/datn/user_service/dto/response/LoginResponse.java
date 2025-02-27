package com.datn.user_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LoginResponse {
    private String userId;
    private String token;
    private String username;
    //    private String ioStreamToken;
    private Set<String> roles;
}
