package com.datn.user_service.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckOTPResponse {
    private boolean isValid;
}
