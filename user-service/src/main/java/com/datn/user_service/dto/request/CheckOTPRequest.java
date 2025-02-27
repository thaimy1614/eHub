package com.datn.user_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckOTPRequest {
    private String email;
    private String otp;
}
