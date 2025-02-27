package com.datn.user_service.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendOTP {
    private String name;
    private String email;
    private String otp;
}
