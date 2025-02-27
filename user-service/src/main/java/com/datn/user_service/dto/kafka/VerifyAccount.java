package com.datn.user_service.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyAccount {
    private String url;
    private String email;
    private String fullName;
}
