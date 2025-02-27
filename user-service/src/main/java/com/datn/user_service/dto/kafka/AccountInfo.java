package com.datn.user_service.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInfo {
    private String email;
    private String fullName;
    private String username;
    private String password;
}
